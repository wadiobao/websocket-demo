package com.example.demo.websocket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.websocket.entities.Room;

public interface RoomRepository extends MongoRepository<Room, String> {
	Optional<Room> findByRoomId(String roomId);
	
	List<Room> findByRoomIdBetweenOrderByRoomIdDesc(String startRoomId, String endRoomId);
	
	List<Room> findByRoomIdLikeOrderByRoomIdDesc(String roomIdPattern);
}
