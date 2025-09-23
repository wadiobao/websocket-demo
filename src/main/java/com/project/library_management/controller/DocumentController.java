package com.project.library_management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;
import com.project.library_management.model.LendRequest;
import com.project.library_management.service.iservice.IDocumentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final IDocumentService documentService;

    @PostMapping
    public ResponseEntity<BaseResponse<DocumentResponse>> createDocument(@Valid @RequestBody DocumentRequest request) {
        return documentService.createDocument(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<DocumentResponse>> updateDocument(@PathVariable("id") Long documentId, @Valid @RequestBody DocumentRequest request) {
        return documentService.updateDocument(documentId, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> deleteDocument(@PathVariable("id") Long documentId) {
        return documentService.deleteDocument(documentId);
    }

    @PostMapping("/lend")
    public ResponseEntity<BaseResponse<Object>> lendDocument(@Valid @RequestBody LendRequest request) {
        return documentService.lendDocument(request);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<BaseResponse<Object>> returnDocument(@PathVariable("id") Long documentId) {
        return documentService.returnDocument(documentId);
    }
}
