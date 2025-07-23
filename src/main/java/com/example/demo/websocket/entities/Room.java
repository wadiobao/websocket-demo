package com.example.demo.websocket.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
	@Id
	private String id;
	private String roomId;
	@Builder.Default
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
}
