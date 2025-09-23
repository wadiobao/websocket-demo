package com.project.library_management.specification;

import com.project.library_management.entity.document.Book;
import com.project.library_management.entity.document.DVD;
import com.project.library_management.entity.document.Magazine;
import com.project.library_management.enums.DocumentType;
import org.springframework.data.jpa.domain.Specification;
import com.project.library_management.entity.document.Document;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DocumentSpecification {

    public static Specification<Document> findByCriteria(String title, String author, String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"));
            }

            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                        "%" + author.toLowerCase() + "%"));
            }

            if (type != null && !type.isEmpty()) {
                try {
                    DocumentType docType = DocumentType.valueOf(type.toUpperCase());
                    switch (docType) {
                        case BOOK:
                            predicates.add(criteriaBuilder.equal(root.type(), Book.class));
                            break;
                        case DVD:
                            predicates.add(criteriaBuilder.equal(root.type(), DVD.class));
                            break;
                        case MAGAZINE:
                            predicates.add(criteriaBuilder.equal(root.type(), Magazine.class));
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    // Invalid type, do not add any predicate for type
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
