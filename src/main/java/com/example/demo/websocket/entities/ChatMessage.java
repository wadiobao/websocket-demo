package com.example.demo.websocket.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.demo.websocket.enums.MessageType;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
	@Id
	private String id;
	private String roomId;
	private LocalDate date;
	private MessageType messageType;
	@NotBlank
	private String sender;
	@NotBlank
	private String content;
	private LocalDateTime timeStamp;
}
