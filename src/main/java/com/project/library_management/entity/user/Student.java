package com.project.library_management.entity.user;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@SuperBuilder
public class Student extends Member {

	@Override
	public int getLoanLimit() {
		return 3; 
	}

	@Override
	public int getLoanDurationDays() {
		return 7; 
	}

	@Override
	public int getFinePerDay() {
		return 2000; 
	}

}
