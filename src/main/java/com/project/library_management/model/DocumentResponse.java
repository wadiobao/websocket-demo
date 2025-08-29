package com.project.library_management.model;

import java.time.LocalDate;

import com.project.library_management.enums.DocumentType;
import com.project.library_management.enums.Language;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DocumentResponse {
    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String author;
    private double price;
    private boolean isBorrowed;
    private Language lang;
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
