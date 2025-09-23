package com.project.library_management.entity.user;

import com.project.library_management.util.Constants;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class Student extends Member {

	@Override
	public int getLoanLimit() {
		return Constants.STUDENT_LOAN_LIMIT; 
	}

	@Override
	public int getLoanDurationDays() {
		return Constants.STUDENT_LOAN_DURATION_DAYS; 
	}

	@Override
	public int getFinePerDay() {
		return Constants.STUDENT_FINE_PER_DAY; 
	}

}
