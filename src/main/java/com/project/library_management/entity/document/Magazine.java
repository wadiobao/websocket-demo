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
	
	@NotNull(message = "{validation.issue_number.not_null}")
	@Min(value = 1, message = "{validation.issue_number.min}")
	int issueNumber;
	
	@NotNull(message = "{validation.pub_frequency.not_null}")
	@Min(value = 1, message = "{validation.pub_frequency.min}")
	int pubFrequency;
}
