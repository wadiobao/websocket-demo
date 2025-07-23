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

import com.example.demo.websocket.services.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RoomController {

	private final RoomService roomService;
	
	@PostMapping("/create")
	public ResponseEntity<?> createRoom(@RequestBody String roomId){
		return roomService.createRoom(roomId);
	}
	
	@PostMapping("/{roomId}")
	public ResponseEntity<?> joinRoom(@PathVariable("roomId") String roomId){
		return roomService.joinRoom(roomId);
	}

	@GetMapping("/{roomId}/messages")
	public ResponseEntity<?> getMessages(
	    @PathVariable("roomId") String roomId,
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "20") int size
	) {
	    return roomService.getMessage(roomId, page, size);
	}
	
}
