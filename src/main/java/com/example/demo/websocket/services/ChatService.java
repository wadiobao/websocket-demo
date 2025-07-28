package com.example.demo.websocket.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.websocket.constant.WebSocketConstant;
import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.entities.Room;
import com.example.demo.websocket.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final RoomRepository roomRepository;
	
	public ChatMessage sendMessage(String roomId,ChatMessage chatMessage) {
		System.out.println(roomId);
		Room room = roomRepository.findByRoomId(roomId).orElseThrow();
		
		ChatMessage message = ChatMessage.builder()
				.messageType(chatMessage.getMessageType())
    			.content(chatMessage.getContent())
    			.sender(chatMessage.getSender())
    			.timeStamp(LocalDateTime.now())
    			.build();
		room.getMessages().add(message);
		roomRepository.save(room);
		
		return message;
	}
}
