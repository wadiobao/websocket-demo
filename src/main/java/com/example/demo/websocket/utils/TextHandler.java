package com.example.demo.websocket.utils;

import com.example.demo.websocket.constant.WebSocketConstant;
import org.springframework.stereotype.Component;

@Component
public class TextHandler {

	public String[] extractUsernames(String roomId) {
        if (roomId == null || !roomId.startsWith(WebSocketConstant.TextHandlerConstants.ROOM_ID_PREFIX)) {
            throw new IllegalArgumentException(WebSocketConstant.TextHandlerConstants.INVALID_ROOM_ID_FORMAT);
        }
        String withoutPrefix = roomId.substring(WebSocketConstant.TextHandlerConstants.ROOM_ID_PREFIX.length());
        String[] parts = withoutPrefix.split(WebSocketConstant.TextHandlerConstants.ROOM_ID_SEPARATOR);
        if (parts.length != 2) {
            throw new IllegalArgumentException(WebSocketConstant.TextHandlerConstants.INVALID_ROOM_ID_FORMAT_EXPECTING_TWO_USERNAMES);
        }
        return parts; // [0] là username1, [1] là username2
    }
	
	}
