package com.example.demo.websocket.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.websocket.constant.WebSocketConstant;
import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.entities.Room;
import com.example.demo.websocket.payload.RoomRequest;
import com.example.demo.websocket.payload.RoomResponse;
import com.example.demo.websocket.repository.ChatMessageRepository;
import com.example.demo.websocket.repository.RoomRepository;
import com.example.demo.websocket.utils.RoomEncryptor;
import com.example.demo.websocket.utils.TextHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository roomRepository;
	private final ChatMessageRepository chatMessageRepository;
	private final TextHandler textHandler;
	private final RoomEncryptor roomEncryptor;
	private String serverKey = WebSocketConstant.RoomServiceConstants.SERVER_KEY;

	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok().body(roomRepository.findAll());
	}

	/**
	 * Lấy roomId theo ngày hiện tại (format: yyyy-MM-dd)
	 */
	public String getTodayRoomId() {
		LocalDate today = LocalDate.now();
		return today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	/**
	 * Lấy roomId theo ngày cụ thể
	 */
	public String getRoomIdByDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public ResponseEntity<?> createRoom(String roomId) {
		if (roomRepository.findByRoomId(roomId).isPresent()) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new RoomResponse().builder().isPresent(true).build());
		}
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
			if (room.getAllowedUser().contains(WebSocketConstant.RoomServiceConstants.DEMO_USER)) {
				return ResponseEntity.ok(room);
			} else {
				return ResponseEntity.badRequest().body(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
	}

	/**
	 * Join phòng - tự động tạo phòng nếu chưa có
	 */
	public ResponseEntity<?> joinRoom(String roomId) {
		Room room = roomRepository.findByRoomId(roomId).orElse(null);
		if(room == null) {
			room = Room.builder().roomId(roomId).build();
			roomRepository.save(room);
			return ResponseEntity.status(HttpStatus.CREATED).body(room);
		}
		return ResponseEntity.ok(room);
	}

	public ResponseEntity<?> getMessage(String roomId, int page, int size) {
		// Kiểm tra phòng có tồn tại không
		Room room = roomRepository.findByRoomId(roomId).orElseThrow();
		
		// Lấy tin nhắn từ collection riêng với phân trang
		Pageable pageable = PageRequest.of(page, size);
		List<ChatMessage> messages = chatMessageRepository.findByRoomIdOrderByTimeStampDesc(roomId, pageable).getContent();
		
		return ResponseEntity.ok(messages);
	}

	/**
	 * Lấy tin nhắn theo ngày hiện tại
	 */
	public ResponseEntity<?> getTodayMessages(int page, int size) {
		LocalDate today = LocalDate.now();
		return getMessagesByDate(today, page, size);
	}

	/**
	 * Lấy tin nhắn theo ngày cụ thể
	 */
	public ResponseEntity<?> getMessagesByDate(LocalDate date, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		List<ChatMessage> messages = chatMessageRepository.findByDateOrderByTimeStampDesc(date, pageable).getContent();
		return ResponseEntity.ok(messages);
	}

	/**
	 * Lấy tin nhắn theo ngày (string format: yyyy-MM-dd)
	 */
	public ResponseEntity<?> getMessagesByDate(String date, int page, int size) {
		LocalDate localDate = LocalDate.parse(date);
		return getMessagesByDate(localDate, page, size);
	}

	/**
	 * Lấy tin nhắn theo khoảng ngày
	 */
	public ResponseEntity<?> getMessagesByDateRange(String startDate, String endDate, int page, int size) {
		LocalDate start = LocalDate.parse(startDate);
		LocalDate end = LocalDate.parse(endDate);
		
		Pageable pageable = PageRequest.of(page, size);
		List<ChatMessage> messages = chatMessageRepository.findByDateBetweenOrderByTimeStampDesc(start, end, pageable).getContent();
		
		return ResponseEntity.ok(messages);
	}

	/**
	 * Lấy tin nhắn theo roomId và ngày
	 */
	public ResponseEntity<?> getMessagesByRoomAndDate(String roomId, String date, int page, int size) {
		LocalDate localDate = LocalDate.parse(date);
		Pageable pageable = PageRequest.of(page, size);
		List<ChatMessage> messages = chatMessageRepository.findByRoomIdAndDateOrderByTimeStampDesc(roomId, localDate, pageable).getContent();
		return ResponseEntity.ok(messages);
	}

	/**
	 * Đếm số tin nhắn theo ngày
	 */
	public ResponseEntity<?> countMessagesByDate(String date) {
		LocalDate localDate = LocalDate.parse(date);
		long count = chatMessageRepository.countByDate(localDate);
		return ResponseEntity.ok(count);
	}

	/**
	 * Đếm số tin nhắn theo roomId và ngày
	 */
	public ResponseEntity<?> countMessagesByRoomAndDate(String roomId, String date) {
		LocalDate localDate = LocalDate.parse(date);
		long count = chatMessageRepository.countByRoomIdAndDate(roomId, localDate);
		return ResponseEntity.ok(count);
	}
}
