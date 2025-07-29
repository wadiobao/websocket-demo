package com.example.demo.websocket.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.websocket.entities.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
	
	List<ChatMessage> findByRoomIdOrderByTimeStampAsc(String roomId);
	
	Page<ChatMessage> findByRoomIdOrderByTimeStampDesc(String roomId, Pageable pageable);
	
	List<ChatMessage> findByDateOrderByTimeStampAsc(LocalDate date);
	
	Page<ChatMessage> findByDateOrderByTimeStampDesc(LocalDate date, Pageable pageable);
	
	List<ChatMessage> findByDateBetweenOrderByTimeStampDesc(LocalDate startDate, LocalDate endDate);
	
	Page<ChatMessage> findByDateBetweenOrderByTimeStampDesc(LocalDate startDate, LocalDate endDate, Pageable pageable);
	
	List<ChatMessage> findByRoomIdAndDateOrderByTimeStampAsc(String roomId, LocalDate date);
	
	Page<ChatMessage> findByRoomIdAndDateOrderByTimeStampDesc(String roomId, LocalDate date, Pageable pageable);
	
	@Query(value = "{'roomId': ?0}", sort = "{'timeStamp': -1}")
	List<ChatMessage> findByRoomIdOrderByTimeStampDescLimit(String roomId, int limit);
	
	long countByRoomId(String roomId);
	
	long countByDate(LocalDate date);
	
	long countByRoomIdAndDate(String roomId, LocalDate date);
}
