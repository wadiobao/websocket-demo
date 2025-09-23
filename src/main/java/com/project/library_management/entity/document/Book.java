package com.project.library_management.entity.document;


import com.project.library_management.util.Constants;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class Book extends Document {
	
	@NotBlank(message = "{validation.isbn.not_blank}")
	@Pattern(regexp = Constants.ISBN_PATTERN, message = "{validation.isbn.invalid}")
	String isbn;
	
	@NotBlank(message = "{validation.subject.not_blank}")
	String subject;
}
