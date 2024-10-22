package com.example.hakaton.service.entity.impl;


import com.example.hakaton.convert.entity.DocumentMapper;
import com.example.hakaton.convert.entity.UploadFileMapper;
import com.example.hakaton.dto.entity.DocumentCommand;
import com.example.hakaton.dto.entity.DocumentQuery;
import com.example.hakaton.dto.entity.UploadFileDTO;
import com.example.hakaton.entity.Document;
import com.example.hakaton.entity.UploadFile;
import com.example.hakaton.exception.CustomError;
import com.example.hakaton.exception.CustomException;
import com.example.hakaton.repository.entity.DocumentRepository;
import com.example.hakaton.repository.entity.UploadFileRepository;
import com.example.hakaton.service.entity.DocumentService;
import com.example.hakaton.util.other.MethodExtensions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AccessLevel;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@ExtensionMethod(MethodExtensions.class)
public class DocumentServiceImpl implements DocumentService {
    DocumentRepository documentRepository;
    UploadFileRepository uploadFileRepository;
    DocumentMapper mapper;
    UploadFileMapper uploadFileMapper;
    @Value("${hakaton.export.fonts}")
    @NonFinal
    String fontPath;
    @Value("${hakaton.export.url}")
    @NonFinal
    String qrUrl;
    @Value("${hakaton.export.path}")
    @NonFinal
    String uploadPath;


    public DocumentServiceImpl(DocumentRepository documentRep,
                               UploadFileRepository uploadFileRepository,
                               DocumentMapper documentMapper,
                               UploadFileMapper uploadFileMapper) {
        this.documentRepository = documentRep;
        this.uploadFileRepository = uploadFileRepository;
        this.mapper = documentMapper;
        this.uploadFileMapper = uploadFileMapper;
    }

    @Override
    public Page<DocumentQuery> getAll(int page, int size, Optional<Boolean> sortOrder, String sortBy) {
        Pageable paging = null;
        if (sortOrder.isPresent()) {
            Sort.Direction direction = null;

            if (sortOrder.get())
                direction = Sort.Direction.ASC;
            else
                direction = Sort.Direction.DESC;

            paging = PageRequest.of(page, size, direction, sortBy);
        } else {
            paging = PageRequest.of(page, size);
        }

        return this.documentRepository.findAll(paging)
                .map(p -> this.mapper.entityToQuery(p, new DocumentQuery()));
    }

    @Override
    public DocumentQuery getByPin(String pin) {
        Document found = documentRepository.findByPin(pin)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        return mapper.entityToQuery(found, new DocumentQuery());
    }

    @Override
    public DocumentQuery getById(String id) {
        Document found = documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        return mapper.entityToQuery(found, new DocumentQuery());
    }

    @Override
    public DocumentQuery create(DocumentCommand command) {
        Document entityToSave = this.mapper.commandToEntity(command);

        Document savedEntity;
        try {
            savedEntity = documentRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.DOCUMENT_NOT_CREATED, e);
        }

        return mapper.entityToQuery(savedEntity, new DocumentQuery());
    }

    @Override
    public DocumentQuery update(DocumentCommand command) {
        String id = command.getId();
        Document found = documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        Document entityToSave = mapper.commandToEntity(command);

        mapper.replaceNullFields(found, entityToSave);

        Document savedEntity;
        try {
            savedEntity = documentRepository.save(entityToSave);
        } catch (Exception e) {
            throw new CustomException(CustomError.DOCUMENT_NOT_UPDATED, e);
        }

        return mapper.entityToQuery(savedEntity, new DocumentQuery());
    }

    @Override
    public void delete(String id) {
        Document entity = this.documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        try {
            documentRepository.deleteById(entity.getId());
        } catch (Exception e) {
            throw new CustomException(CustomError.DOCUMENT_NOT_DELETED, e);
        }
    }

    @Override
    public UploadFileDTO getFileById(String id) {
        UploadFile entity = this.uploadFileRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.FILE_NOT_FOUND));

        return this.uploadFileMapper.entityToQuery(entity, new UploadFileDTO());
    }

    @Override
    public List<UploadFileDTO> getFilesByID(String id) {
        Document entity = this.documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        List<UploadFile> list = new ArrayList<>();
        if (entity.getFiles() != null) {
            for (UploadFile i : entity.getFiles()) {
                if (i != null)
                    list.add(this.uploadFileRepository.findById(i.getId())
                            .orElseThrow(() -> new CustomException(CustomError.FILE_NOT_FOUND)));
            }
        }

        return list.stream().map(i -> {
            return uploadFileMapper.entityToQuery(i, new UploadFileDTO());
        }).collect(Collectors.toList());
    }

    @Override
    public void upload(String id, List<MultipartFile> uploadfiles) throws IOException {
        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (MultipartFile file : uploadfiles) {
            if (file.isEmpty())
                continue;

            if (file.getContentType().equals(MediaType.IMAGE_PNG_VALUE) || file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(file.getOriginalFilename());

                save(path, bytes, file.getSize(), id);
            } else
                throw new CustomException(CustomError.FILE_TYPE_NOT_PICTURE);
        }
    }

    public void save(Path path, byte[] bytes, Long size, String id) throws IOException {
        Document entity = this.documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        Path fullPath;
        UploadFile file = uploadFileMapper.commandToEntity(
                new UploadFileDTO(null, path.toString(), size, null, null));

        entity.getFiles().add(file);

        try {
            documentRepository.save(entity);
        } catch (Exception e) {
            throw new CustomException(CustomError.DOCUMENT_NOT_UPDATED, e);
        }

        UploadFileDTO result = this.uploadFileMapper.entityToQuery(file, new UploadFileDTO());
        Files.write(Paths.get(result.getPath()), bytes);
    }

    @Override
    public PDDocument export(String id) throws IOException, WriterException {
        Document entity = this.documentRepository.findById(id)
                .orElseThrow(() -> new CustomException(CustomError.DOCUMENT_NOT_FOUND));

        DocumentQuery response = mapper.entityToQuery(entity, new DocumentQuery());

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        int fontSize = 12;
        float xMax = page.getCropBox().getWidth(); //595
        float yMax = page.getCropBox().getHeight(); //841

        PDPageContentStream stream = new PDPageContentStream(document, page);
        PDType0Font font = PDType0Font.load(document, new File(fontPath + "times.ttf"));
        PDType0Font fontbd = PDType0Font.load(document, new File(fontPath + "timesbd.ttf"));
        stream.setFont(font, fontSize);

        //QR code
        String text = qrUrl + id;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200, hintMap);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();

        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        File qr = new File("qr.png");
        ImageIO.write(bufferedImage, "png", qr);

        PDImageXObject pdImage = PDImageXObject.createFromFileByContent(qr, document);
        stream.drawImage(pdImage, xMax - 225, yMax - 190);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        stream.writeXCenterB("Название организации", fontSize, font, xMax - 200, 25, yMax - 30, document);

        String ozFullName = (response.getOrganization() == null) ? "Название организации" : response.getOrganization().getOzFullName();
        stream.setFont(fontbd, 12);
        stream.writeSW(ozFullName, 12, fontbd, xMax - 265, yMax, 0, 50, yMax - 50);
        stream.setFont(font, fontSize);


        stream.writeXCenterB("Сведения обследования: ", fontSize, font, xMax, 0, yMax - 200, document);

        stream.writeText("Дата проведения: " + response.getDateAnaliza().toDate(), 50, yMax - 220);
        String type = (response.getObsledovanie() == null) ? "Название обследования" : response.getObsledovanie().getName();
        stream.writeText("Тип обследования: " + type, 50, yMax - 235);


        stream.writeXCenterB("Сведения пациента: ", fontSize, font, xMax, 0, yMax - 270, document);

        String sex = (entity.getSex() == null) ? "" : (entity.getSex() == 1) ? "Мужской" : "Женский";
        String[] patientInfo = new String[]{
                "ПИН: " + response.getPin(),
                "Дата рождения: " + response.getBirthDate().toDate(),
                "Пол: " + sex,
        };
        stream.writeSW(patientInfo, fontSize, font, xMax, 50, 0, yMax - 290);

        String[] patientFIO = new String[]{
                "Фамилия: " + response.getSurname(),
                "Имя: " + response.getName(),
                "Отчество: " + response.getPatronymic()
        };
        stream.writeSW(patientFIO, fontSize, font, xMax, 50, 0, yMax - 305);

        stream.writeXCenter("Заключение: ", fontSize, font, xMax, 0, yMax - 330);
        stream.writeSW(response.getZaklyuchenie(), fontSize, font, xMax, yMax, 50, 0, yMax - 350);

        stream.writeXCenter("Описание: ", fontSize, font, xMax, 0, yMax - 520);
        stream.writeSW(response.getDescription(), fontSize, font, xMax, yMax, 50, 0, yMax - 540);

        String fio = (response.getDoctor() == null) ? "ФИО врача" : response.getDoctor().getFIO();
        stream.writeText("ФИО врача: " + fio, 50, yMax - 800);

        stream.close();
        return document;
    }

}
