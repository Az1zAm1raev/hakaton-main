package com.example.hakaton.dto.entity;

import com.example.hakaton.dto.CommandDTO;
import com.example.hakaton.dto.QueryDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.nio.file.Path;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadFileDTO implements QueryDTO, CommandDTO {
    String id;
    String name;
    Long size;
    String path;
    Path fullPath;
}
