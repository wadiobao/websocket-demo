package com.project.library_management.entity.document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.project.library_management.entity.BaseModel;
import com.project.library_management.entity.DocLending;
import com.project.library_management.enums.Language;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class Document extends BaseModel {
	
	@NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
	String title;
	
	@NotNull(message = "Publication date cannot be null")
    @PastOrPresent(message = "Publication date cannot be in the future")
	LocalDate publicationDate;
	
	@NotNull(message = "Language cannot be null")
	Language lang;
	
	@NotBlank(message = "Author cannot be blank")
    @Size(max = 255, message = "Author cannot exceed 255 characters")
	String author;
	
	boolean isReferenceOnly;
	
	boolean borrowed;
	
	LocalDateTime dueDate;
	
	@NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price cannot be negative")
	double price;
	
	@ManyToOne
    @JoinColumn(name = "rack_id", nullable = false)
	Rack rack;
	
	@OneToOne
	DocLending docLending;
	
	public boolean checkout(String memberId) {
		return true;
	}
}
