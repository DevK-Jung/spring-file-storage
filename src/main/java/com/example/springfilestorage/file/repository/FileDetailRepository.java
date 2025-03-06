package com.example.springfilestorage.file.repository;

import com.example.springfilestorage.file.entity.FileDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailRepository extends JpaRepository<FileDetailEntity, Long> {
}
