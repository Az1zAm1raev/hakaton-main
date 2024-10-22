package com.example.hakaton.controller;

import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.dto.entity.DocumentQuery;
import com.example.hakaton.dto.entity.UploadFileDTO;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.service.entity.DocumentService;
import com.google.zxing.WriterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document")
public class DocumentController {
    private final DocumentService service;

    @Autowired
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Page<DocumentQuery> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                                      @RequestParam(required = false, defaultValue = "25") int size,
                                      @RequestParam(required = false) Optional<Boolean> sortOrder,
                                      @RequestParam(required = false) String sortBy) {
        return service.getAll(page, size, sortOrder, sortBy);
    }

    @PostMapping("/create")
    public ResponseEntity<DocumentQuery> create(@Valid @RequestBody DocumentCommand command) {
        DocumentQuery saveEntity = service.create(command);
        return new ResponseEntity<>(saveEntity, HttpStatus.CREATED);
    }

    @GetMapping("/getById/{id}")
    public DocumentQuery getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/getByPin")
    public DocumentQuery getByPin(@RequestParam String pin) {
        return service.getByPin(pin);
    }

    @GetMapping("/export/{id}")
    public HttpEntity<byte[]> export(@PathVariable String id) throws IOException, WriterException {
        String fileName = ZonedDateTime.now().toString() + ".pdf";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PDDocument document = service.export(id);
        document.save(baos);
        document.close();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        header.setContentLength(baos.size());
        return new HttpEntity<byte[]>(baos.toByteArray(), header);
    }

    @PutMapping("/update")
    public ResponseEntity<DocumentQuery> updateDocument(@Valid @RequestBody DocumentCommand command) {
        DocumentQuery updatedEntity = service.update(command);
        return new ResponseEntity<>(updatedEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PostMapping(value = "/multipleupload", consumes = {"multipart/form-data"})
    public ResponseEntity uploadFiles(@RequestParam String documentId, @RequestPart(required = true) MultipartFile[] uploadfiles) {
        String uploadedFileName = Arrays.stream(uploadfiles)
                .map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x))
                .collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName))
            throw new CustomException(CustomError.SELECT_NO_FILES);

        try {
            service.upload(documentId, Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }

    @GetMapping("/getFileById/{id}")
    public ResponseEntity<ByteArrayResource> getFileById(String fileId) throws IOException {
        UploadFileDTO file = this.service.getFileById(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.getFullPath()));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.getSize())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/getFilesByDocumentId/{id}")
    public List<UploadFileDTO> getFilesByDocumentId(String documentId) {
        return this.service.getFilesByID(documentId);
    }
}
