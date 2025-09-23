package com.project.library_management.service.iservice;

import com.project.library_management.model.BaseResponse;
import com.project.library_management.model.DocumentResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISearchService {
    ResponseEntity<BaseResponse<List<DocumentResponse>>> searchDocuments(String title, String author, String type);
}
