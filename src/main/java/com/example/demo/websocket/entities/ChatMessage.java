package com.example.demo.websocket.entities;

import java.time.LocalDateTime;

import com.example.demo.websocket.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
	private MessageType messageType;
	private String sender;
	private String content;
	private LocalDateTime timeStamp;
	
	public ChatMessage(MessageType messageType, String sender, String content) {
		this.messageType = messageType;
		this.sender = sender;
		this.content = content;
		this.timeStamp = LocalDateTime.now() ;
	}
	
	
}
