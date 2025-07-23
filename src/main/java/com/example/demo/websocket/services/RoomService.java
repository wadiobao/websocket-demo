package com.example.demo.websocket.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.entities.Room;
import com.example.demo.websocket.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository roomRepository;
	
	public ResponseEntity<?> createRoom(String roomId) {
		if(roomRepository.findByRoomId(roomId)!=null)
			return ResponseEntity.badRequest().body("Room already exists");
		
		Room room = Room.builder().roomId(roomId).build();
		roomRepository.save(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(room);
	}
	
	public ResponseEntity<?> joinRoom(String roomId) {
		Room room = roomRepository.findByRoomId(roomId).orElse(null);
		if(room == null) {
			room = Room.builder().roomId(roomId).build();
			roomRepository.save(room);
			return ResponseEntity.status(HttpStatus.CREATED).body(room);
		}
		return ResponseEntity.ok(room);
	}
	
	public ResponseEntity<?> getMessage(String roomId, int page, int size){
		Room room = roomRepository.findByRoomId(roomId).orElseThrow();
		if(room==null)
			return ResponseEntity.badRequest().build();
			
		List<ChatMessage> messages = room.getMessages();
		
		int start = Math.max(0, messages.size()-(page+1)*size);
		int end = Math.min(messages.size(), start+size);
		
		List<ChatMessage> paginatedMessages = messages.subList(start, end);
		
		return ResponseEntity.ok(paginatedMessages);
	}
	
}
