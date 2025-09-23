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
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public abstract class Member extends User {
	LocalDate dateOfMembersip;
	int totalCheckedout;
	double fine;
	
	public void addFine(double amount) {
		this.fine += amount;
	}
	
	public void payFine(double amount) {
		if (amount > this.fine) {
			this.fine = 0;
		} else {
			this.fine -= amount;
		}
	}
	
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
	
	public abstract int getLoanLimit();
	public abstract int getLoanDurationDays();
	public abstract int getFinePerDay();
}
