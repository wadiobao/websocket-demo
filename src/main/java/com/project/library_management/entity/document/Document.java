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
@FieldDefaults(level = AccessLevel.PRIVATE)
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class Document extends BaseModel {
	
	@NotBlank(message = "{validation.title.not_blank}")
    @Size(max = 255, message = "{validation.title.too_long}")
	String title;
	
	@NotNull(message = "{validation.publication_date.not_null}")
    @PastOrPresent(message = "{validation.publication_date.past_or_present}")
	LocalDate publicationDate;
	
	@NotNull(message = "{validation.language.not_null}")
	Language lang;
	
	@NotBlank(message = "{validation.author.not_blank}")
    @Size(max = 255, message = "{validation.author.too_long}")
	String author;
	
	boolean isReferenceOnly;
	
	boolean borrowed;
	
	LocalDateTime dueDate;
	
	@NotNull(message = "{validation.price.not_null}")
    @Min(value = 0, message = "{validation.price.negative}")
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
