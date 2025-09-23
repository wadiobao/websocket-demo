package com.project.library_management.mapper;

import com.project.library_management.entity.document.Book;
import com.project.library_management.entity.document.DVD;
import com.project.library_management.entity.document.Document;
import com.project.library_management.entity.document.Magazine;
import com.project.library_management.enums.DocumentType;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;

public final class DocumentMapper {

    private DocumentMapper() {}

    public static Document toDocumentEntity(DocumentRequest request) {
        switch (request.getType()) {
            case BOOK:
                return Book.builder()
                        .title(request.getTitle())
                        .publicationDate(request.getPublicationDate())
                        .author(request.getAuthor())
                        .price(request.getPrice())
                        .lang(request.getLang())
                        .isbn(request.getIsbn())
                        .subject(request.getSubject())
                        .build();
            case DVD:
                return DVD.builder()
                        .title(request.getTitle())
                        .publicationDate(request.getPublicationDate())
                        .author(request.getAuthor())
                        .price(request.getPrice())
                        .lang(request.getLang())
                        .duration(request.getDuration())
                        .genre(request.getGenre())
                        .build();
            case MAGAZINE:
                return Magazine.builder()
                        .title(request.getTitle())
                        .publicationDate(request.getPublicationDate())
                        .author(request.getAuthor())
                        .price(request.getPrice())
                        .lang(request.getLang())
                        .issueNumber(request.getIssueNumber())
                        .pubFrequency(request.getPubFrequency())
                        .build();
            default:
                throw new IllegalArgumentException("Invalid document type: " + request.getType());
        }
    }

    public static DocumentResponse toDocumentResponse(Document document) {
        DocumentResponse.DocumentResponseBuilder builder = DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .publicationDate(document.getPublicationDate())
                .author(document.getAuthor())
                .price(document.getPrice())
                .isBorrowed(document.isBorrowed())
                .lang(document.getLang());

        if (document instanceof Book) {
            Book book = (Book) document;
            builder.type(DocumentType.BOOK).isbn(book.getIsbn()).subject(book.getSubject());
        } else if (document instanceof DVD) {
            DVD dvd = (DVD) document;
            builder.type(DocumentType.DVD).duration(dvd.getDuration()).genre(dvd.getGenre());
        } else if (document instanceof Magazine) {
            Magazine magazine = (Magazine) document;
            builder.type(DocumentType.MAGAZINE).issueNumber(magazine.getIssueNumber()).pubFrequency(magazine.getPubFrequency());
        }
        return builder.build();
    }
}
