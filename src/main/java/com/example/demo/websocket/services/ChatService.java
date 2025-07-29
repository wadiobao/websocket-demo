package com.example.demo.websocket.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.entities.Room;
import com.example.demo.websocket.repository.ChatMessageRepository;
import com.example.demo.websocket.repository.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
	
	private final RoomRepository roomRepository;
	private final ChatMessageRepository chatMessageRepository;
	
	public ChatMessage sendMessage(String roomId, ChatMessage chatMessage) {

		Room room = roomRepository.findByRoomId(roomId).orElseThrow();
		
		ChatMessage message = ChatMessage.builder()
				.roomId(roomId)
				.date(LocalDate.now()) 
				.messageType(chatMessage.getMessageType())
    			.content(chatMessage.getContent())
    			.sender(chatMessage.getSender())
    			.timeStamp(LocalDateTime.now())
    			.build();
		
		return chatMessageRepository.save(message);
	}
}
