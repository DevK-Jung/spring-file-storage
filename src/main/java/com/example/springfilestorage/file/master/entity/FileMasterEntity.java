package com.example.springfilestorage.file.master.entity;

import com.example.springfilestorage.core.entity.BaseEntity;
import com.example.springfilestorage.file.detail.entity.FileDetailEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file_master")
public class FileMasterEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_SEQ")
    private Long fileSeq;
    @Column(name = "FILE_TYPE", nullable = false, length = 50)
    private String fileType;
    @OneToMany(mappedBy = "fileMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileDetailEntity> fileDetails = new ArrayList<>();
}