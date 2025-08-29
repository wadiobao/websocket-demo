package com.project.library_management.entity.document;

import java.util.List;

import com.project.library_management.entity.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Rack {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@NotNull(message = "{validation.rack.number.not_null}")
	int number;
	
	@NotBlank(message = "{validation.rack.location.not_blank}")
	String location;
	
	@OneToMany(mappedBy = "rack")
	List<Document> documents;
}
