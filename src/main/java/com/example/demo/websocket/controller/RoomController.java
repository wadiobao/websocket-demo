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

	@GetMapping("/today/messages")
	public ResponseEntity<?> getTodayMessages(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size
	) {
		return roomService.getTodayMessages(page, size);
	}
	
	@GetMapping("/date/{date}/messages")
	public ResponseEntity<?> getMessagesByDate(
		@PathVariable("date") String date,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size
	) {
		return roomService.getMessagesByDate(date, page, size);
	}
	
	@GetMapping("/date-range/messages")
	public ResponseEntity<?> getMessagesByDateRange(
		@RequestParam String startDate,
		@RequestParam String endDate,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size
	) {
		return roomService.getMessagesByDateRange(startDate, endDate, page, size);
	}

	@GetMapping("/{roomId}/date/{date}/messages")
	public ResponseEntity<?> getMessagesByRoomAndDate(
		@PathVariable("roomId") String roomId,
		@PathVariable("date") String date,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "20") int size
	) {
		return roomService.getMessagesByRoomAndDate(roomId, date, page, size);
	}

	@GetMapping("/date/{date}/count")
	public ResponseEntity<?> countMessagesByDate(@PathVariable("date") String date) {
		return roomService.countMessagesByDate(date);
	}

	@GetMapping("/{roomId}/date/{date}/count")
	public ResponseEntity<?> countMessagesByRoomAndDate(
		@PathVariable("roomId") String roomId,
		@PathVariable("date") String date
	) {
		return roomService.countMessagesByRoomAndDate(roomId, date);
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
