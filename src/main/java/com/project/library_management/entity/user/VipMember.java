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
public class VipMember extends Member {

	@Override
	public int getLoanLimit() {
		return 10; 
	}

	@Override
	public int getLoanDurationDays() {
		return 30; 
	}

	@Override
	public int getFinePerDay() {
		return 500; 
	}

}
