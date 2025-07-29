package com.example.demo.websocket.payload;

import com.example.demo.websocket.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {
	private MessageType type;
    private String message;
}
