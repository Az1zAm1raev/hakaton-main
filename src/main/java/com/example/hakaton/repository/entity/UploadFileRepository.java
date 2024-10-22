package com.example.hakaton.repository.entity;

import com.example.hakaton.entity.Document;
import com.example.hakaton.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, String> {
}

