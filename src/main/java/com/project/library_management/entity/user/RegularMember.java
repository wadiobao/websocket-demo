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
public class RegularMember extends Member{

	@Override
	public int getLoanLimit() {
		return 5; 
	}

	@Override
	public int getLoanDurationDays() {
		return 14; 
	}

	@Override
	public int getFinePerDay() {
		return 1000; 
	}

}
