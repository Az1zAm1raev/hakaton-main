package com.example.hakaton.service.entity;

import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.dto.entity.DocumentQuery;
import com.example.hakaton.dto.entity.UploadFileDTO;
import com.google.zxing.WriterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Page<DocumentQuery> getAll(int page,
                               int size,
                               Optional<Boolean> sortOrder,
                               String sortBy);

    DocumentQuery getByPin(String pin);

    DocumentQuery getById(String id);

    PDDocument export(String id) throws IOException, WriterException;

    DocumentQuery create(DocumentCommand command);

    DocumentQuery update(DocumentCommand command);

    void delete(String id);


//    PDDocument export(String id) throws IOException;
    void upload(String id, List<MultipartFile> uploadfiles) throws IOException;

    UploadFileDTO getFileById(String id);
    List<UploadFileDTO> getFilesByID(String id);

}
