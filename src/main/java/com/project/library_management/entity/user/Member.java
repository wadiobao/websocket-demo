package com.project.library_management.entity.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@SuperBuilder
public abstract class Member extends User {
	LocalDate dateOfMembersip;
	int totalCheckedout;
	
	public void incrementTotalCheckedout() {
		if (totalCheckedout < getLoanLimit()) {
			totalCheckedout++;
		}
	}
	
	public void checkoutDocItem() {
		if (totalCheckedout < getLoanLimit()) {
			totalCheckedout++;
		}
	}
	
	public void returnDocItem()	{
		if (totalCheckedout > 0) {
			totalCheckedout--;
		}
	}
	
	public void checkForFine() {
		if (totalCheckedout > 0) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime dueDate = now.plusDays(getLoanDurationDays());
			if (now.isAfter(dueDate)) {
				long daysLate = ChronoUnit.DAYS.between(dueDate, now);
				int fineAmount = (int) (daysLate * getFinePerDay());
			}
		}
	}
	
	public abstract int getLoanLimit();
	public abstract int getLoanDurationDays();
	public abstract int getFinePerDay();
}
