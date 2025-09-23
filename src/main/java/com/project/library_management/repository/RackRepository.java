package com.project.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.library_management.entity.document.Rack;

@Repository
public interface RackRepository extends JpaRepository<Rack, Long> {
}
