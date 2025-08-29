package com.project.library_management.service.iservice;

import org.springframework.http.ResponseEntity;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.DocumentRequest;
import com.project.library_management.model.DocumentResponse;
import com.project.library_management.model.LendRequest;

import jakarta.validation.Valid;

public interface IDocumentService {
    ResponseEntity<BaseResponse<DocumentResponse>> createDocument(@Valid DocumentRequest request);
    ResponseEntity<BaseResponse<DocumentResponse>> updateDocument(Long documentId, @Valid DocumentRequest request);
    ResponseEntity<BaseResponse<Object>> deleteDocument(Long documentId);
    ResponseEntity<BaseResponse<Object>> lendDocument(@Valid LendRequest request);
    ResponseEntity<BaseResponse<Object>> returnDocument(Long documentId);
}
