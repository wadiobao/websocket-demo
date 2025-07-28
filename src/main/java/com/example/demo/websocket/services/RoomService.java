package com.example.demo.websocket.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.websocket.constant.WebSocketConstant;
import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.entities.Room;
import com.example.demo.websocket.payload.RoomRequest;
import com.example.demo.websocket.payload.RoomResponse;
import com.example.demo.websocket.repository.RoomRepository;
import com.example.demo.websocket.utils.RoomEncryptor;
import com.example.demo.websocket.utils.TextHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository roomRepository;

	private final TextHandler textHandler;

	private final RoomEncryptor roomEncryptor;

	private String serverKey = WebSocketConstant.RoomServiceConstants.SERVER_KEY;

	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(roomRepository.findAll());
	}

	public ResponseEntity<?> createRoom(String roomId) {
		if (roomRepository.findByRoomId(roomId).isPresent())
			return ResponseEntity.status(HttpStatus.FOUND).body(new RoomResponse().builder().isPresent(true).build());
		try {
			List<String> allowedUsers = List.of(textHandler.extractUsernames(roomId));
			String publicKey = roomEncryptor.generateRandomKey16Bytes();
			String secretKey = roomEncryptor.encrypt(serverKey, publicKey);
			String encryptedRoomId = roomEncryptor.encrypt(roomId, secretKey);

			Room room = Room.builder().roomId(roomId).locked(true).secretKey(secretKey).allowedUser(allowedUsers)
					.build();
			roomRepository.save(room);
			return ResponseEntity.status(HttpStatus.CREATED).body(new RoomResponse().builder().roomKey(encryptedRoomId)
					.publicKey(publicKey).isPresent(false).build());
		} catch (Exception e) {

		}
		return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> joinRoom(RoomRequest roomRequest) {
		try {
			String secretKey = roomEncryptor.encrypt(serverKey, roomRequest.getPublicKey());
			String roomId = roomEncryptor.decrypt(roomRequest.getRoomKey(), secretKey);
			Room room = roomRepository.findByRoomId(roomId).orElse(null);
			if (room.getAllowedUser().contains(WebSocketConstant.RoomServiceConstants.DEMO_USER))
				return ResponseEntity.ok(room);
			else
				return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> getMessage(String roomId, int page, int size) {
		Room room = roomRepository.findByRoomId(roomId).orElseThrow();
		if (room == null)
			return ResponseEntity.badRequest().build();

		List<ChatMessage> messages = room.getMessages();

		int start = Math.max(0, messages.size() - (page + 1) * size);
		int end = Math.min(messages.size(), start + size);

		List<ChatMessage> paginatedMessages = messages.subList(start, end);

		return ResponseEntity.ok(paginatedMessages);
	}

}
