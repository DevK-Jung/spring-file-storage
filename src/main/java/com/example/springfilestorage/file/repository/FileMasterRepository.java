package com.example.springfilestorage.file.repository;

import com.example.springfilestorage.file.entity.FileMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMasterRepository extends JpaRepository<FileMasterEntity, Long> {
}
