package com.example.demo.websocket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.websocket.constant.WebSocketConstant;
import com.example.demo.websocket.payload.RoomRequest;
import com.example.demo.websocket.services.RoomService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(WebSocketConstant.RoomControllerConstants.ROOM_ENDPOINT)
@RequiredArgsConstructor
@CrossOrigin
public class RoomController {

	private final RoomService roomService;
	
	@PostMapping(WebSocketConstant.RoomControllerConstants.CREATE_ROOM_ENDPOINT)
	public ResponseEntity<?> createRoom(@NotBlank @RequestParam String roomId) {
		return roomService.createRoom(roomId);
	}
	
	@PostMapping(WebSocketConstant.RoomControllerConstants.JOIN_ROOM_ENDPOINT)
	public ResponseEntity<?> joinRoom(@Valid @RequestBody RoomRequest roomRequest) {
		return roomService.joinRoom(roomRequest);
	}

	@GetMapping(WebSocketConstant.RoomControllerConstants.MESSAGES_ENDPOINT)
	public ResponseEntity<?> getMessages(
	    @PathVariable(WebSocketConstant.RoomControllerConstants.ROOM_ID_PATH_VARIABLE) String roomId,
	    @RequestParam(defaultValue = WebSocketConstant.RoomControllerConstants.PAGE_DEFAULT_VALUE) int page,
	    @RequestParam(defaultValue = WebSocketConstant.RoomControllerConstants.SIZE_DEFAULT_VALUE) int size
	) {
	    return roomService.getMessage(roomId, page, size);
	}
	
	@GetMapping(WebSocketConstant.RoomControllerConstants.ALL_ROOMS_ENDPOINT)
	public ResponseEntity<?> getAll(){
		return roomService.getAll();
	}
	
}
