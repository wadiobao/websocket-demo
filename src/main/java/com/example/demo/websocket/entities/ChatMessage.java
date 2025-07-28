package com.example.demo.websocket.entities;

import java.time.LocalDateTime;

import com.example.demo.websocket.enums.MessageType;

import jakarta.validation.constraints.NotBlank;
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
	@NotBlank
	private String sender;
	@NotBlank
	private String content;
	private LocalDateTime timeStamp;
}
