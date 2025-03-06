package com.example.springfilestorage.file.detail.repository;

import com.example.springfilestorage.file.detail.entity.FileDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDetailRepository extends JpaRepository<FileDetailEntity, Long> {
}
