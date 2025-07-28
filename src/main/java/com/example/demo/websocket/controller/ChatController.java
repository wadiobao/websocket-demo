package com.example.demo.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.websocket.constant.WebSocketConstant;
import com.example.demo.websocket.entities.ChatMessage;
import com.example.demo.websocket.services.ChatService;

import jakarta.validation.Valid;

@Controller
@CrossOrigin()
public class ChatController {
	
	@Autowired
	private ChatService chatService;

    @MessageMapping(WebSocketConstant.ChatControllerConstants.SEND_MESSAGE_ENDPOINT)
    @SendTo(WebSocketConstant.ChatControllerConstants.TOPIC_ROOM)
    public ChatMessage sendMessage(@DestinationVariable String roomId ,@Valid @Payload ChatMessage chatMessage) {
          	
        return chatService.sendMessage(roomId,chatMessage);
        
    }

    @MessageMapping(WebSocketConstant.ChatControllerConstants.ADD_USER_ENDPOINT)
    @SendTo(WebSocketConstant.WebSocketEventListenerConstants.TOPIC_PUBLIC)
    public ChatMessage addUser(@Valid @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put(WebSocketConstant.WebSocketEventListenerConstants.USERNAME, chatMessage.getSender());
        return chatMessage;
        
    }

    @MessageMapping(WebSocketConstant.ChatControllerConstants.SEND_MESSAGE_TO_PUBLIC_ENDPOINT)
    @SendTo(WebSocketConstant.WebSocketEventListenerConstants.TOPIC_PUBLIC)
    public ChatMessage sendMessageToPublic(@Valid @Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
