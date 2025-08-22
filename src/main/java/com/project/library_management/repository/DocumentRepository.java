package com.project.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.library_management.entity.document.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {

}
