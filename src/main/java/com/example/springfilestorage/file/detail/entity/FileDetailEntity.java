package com.example.springfilestorage.file.detail.entity;

import com.example.springfilestorage.core.entity.BaseEntity;
import com.example.springfilestorage.file.master.entity.FileMasterEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "file_detail")
public class FileDetailEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_DETAIL_SEQ")
    private Long fileDetailSeq;
    @Column(name = "OGC_FILE_NM", nullable = false, length = 255)
    private String ogcFileNm;
    @Column(name = "SAVE_FILE_NM", nullable = false, length = 255)
    private String saveFileNm;
    @Column(name = "FILE_PATH_VAL", nullable = false, length = 500)
    private String filePathVal;
    @Column(name = "FILE_EXTN", nullable = false, length = 10)
    private String fileExtn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FILE_SEQ", nullable = false)
    private FileMasterEntity fileMaster;
}
