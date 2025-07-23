package com.example.demo.websocket.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
	private String content;
	private String sender;
	private String roomId;
	private LocalDateTime dateTime;

}
