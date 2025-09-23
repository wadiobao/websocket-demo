package com.project.library_management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.library_management.entity.DocLending;

@Repository
public interface DocLendingRepository extends JpaRepository<DocLending, Long> {
    Optional<DocLending> findByDocId_IdAndReturnDateIsNull(Long documentId);
}
