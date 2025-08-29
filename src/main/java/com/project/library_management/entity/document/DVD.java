package com.project.library_management.entity.document;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
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
public class DVD extends Document {
	
	@NotBlank(message = "{validation.duration.not_blank}")
	String duration;
	
	@NotBlank(message = "{validation.genre.not_blank}")
	String genre;
}
