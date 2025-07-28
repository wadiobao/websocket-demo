package com.example.demo.websocket.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
	@NotBlank
	private String roomId;	
	private boolean locked;
	private String secretKey;
	@Builder.Default
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	@Default
	private List<String> allowedUser = new ArrayList<String>();
}
