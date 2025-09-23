package com.project.library_management.model;

import java.time.LocalDate;

import com.project.library_management.enums.DocumentType;
import com.project.library_management.enums.Language;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentRequest {
    @NotBlank(message = "{validation.title.not_blank}")
    private String title;

    @NotNull(message = "{validation.publication_date.not_null}")
    @PastOrPresent(message = "{validation.publication_date.past_or_present}")
    private LocalDate publicationDate;

    @NotBlank(message = "{validation.author.not_blank}")
    private String author;

    @NotNull(message = "{validation.price.not_null}")
    @PositiveOrZero(message = "{validation.price.positive_or_zero}")
    private double price;

    @NotNull(message = "{validation.rack_id.not_null}")
    private Long rackId;

    @NotNull(message = "{validation.language.not_null}")
    private Language lang;

    @NotNull(message = "{validation.document_type.not_null}")
    private DocumentType type;

    // Book specific
    private String isbn;
    private String subject;

    // DVD specific
    private String duration;
    private String genre;

    // Magazine specific
    private int issueNumber;
    private int pubFrequency;
}
