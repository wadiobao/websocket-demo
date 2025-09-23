package com.project.library_management.entity;

import java.time.LocalDateTime;

import com.project.library_management.entity.document.Document;
import com.project.library_management.entity.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DocLending extends BaseModel {
	
	@OneToOne(mappedBy = "docLending")
	Document docId;
	
	LocalDateTime creationDate;
	LocalDateTime dueDate;
	LocalDateTime returnDate;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	User userId;

}
