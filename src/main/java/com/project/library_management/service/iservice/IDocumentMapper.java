package com.project.library_management.service.iservice;

import com.project.library_management.entity.document.Document;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;

public interface IDocumentMapper {
    Document toDocumentEntity(DocumentRequest request);
    DocumentResponse toDocumentResponse(Document document);
}
