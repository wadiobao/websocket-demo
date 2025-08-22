package com.project.library_management.entity.document;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class Magazine extends Document {
	
	@NotNull(message = "Issue number cannot be null")
	@Min(value = 1, message = "Issue number must be greater than 0")
	int issueNumber;
	
	@NotNull(message = "Publication frequency cannot be null")
	@Min(value = 1, message = "Publication frequency must be greater than 0")
	int pubFrequency;
}
