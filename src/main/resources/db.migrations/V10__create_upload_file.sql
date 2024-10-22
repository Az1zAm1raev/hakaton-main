CREATE TABLE upload_file
(
    id   VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    path VARCHAR(255),
    size BIGINT,
    CONSTRAINT pk_uploadfile PRIMARY KEY (id)
);