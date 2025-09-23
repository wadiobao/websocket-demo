package com.project.library_management.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;
import com.project.library_management.model.LendRequest;
import com.project.library_management.service.iservice.IDocumentService;
import com.project.library_management.service.iservice.ISearchService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentController {

    IDocumentService documentService;
    ISearchService searchService;

    @GetMapping
    @PreAuthorize("hasAnyRole('LIBRARIAN', 'MEMBER')")
    public ResponseEntity<BaseResponse<List<DocumentResponse>>> searchDocuments(@RequestParam(required = false) String title,
                                                                              @RequestParam(required = false) String author,
                                                                              @RequestParam(required = false) String type) {
        return searchService.searchDocuments(title, author, type);
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<DocumentResponse>> createDocument(@Valid @RequestBody DocumentRequest request) {
        return documentService.createDocument(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<DocumentResponse>> updateDocument(@PathVariable("id") Long documentId, @Valid @RequestBody DocumentRequest request) {
        return documentService.updateDocument(documentId, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<Object>> deleteDocument(@PathVariable("id") Long documentId) {
        return documentService.deleteDocument(documentId);
    }

    @PostMapping("/lend")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<Object>> lendDocument(@Valid @RequestBody LendRequest request) {
        return documentService.lendDocument(request);
    }

    @PostMapping("/return/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BaseResponse<Object>> returnDocument(@PathVariable("id") Long documentId) {
        return documentService.returnDocument(documentId);
    }
}
