CREATE TABLE document
(
    id                 VARCHAR(255) NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted            BOOLEAN      NOT NULL,
    deleted_by         VARCHAR(255),
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,

    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    birth_date         TIMESTAMP WITHOUT TIME ZONE,
    date_analiza       TIMESTAMP WITHOUT TIME ZONE,
    description        TEXT,
    name               VARCHAR(255),
    patronymic         VARCHAR(255),
    pin                VARCHAR(255),

    sex                BIGINT,
    surname            VARCHAR(255),

    zaklyuchenie       TEXT,
    doctor_id          BIGINT,

    obsledovanie_id    BIGINT,
    organization_id    BIGINT,
    CONSTRAINT pk_document PRIMARY KEY (id)
);

CREATE TABLE document_files
(
    document_id VARCHAR(255) NOT NULL,
    files_id    VARCHAR(255) NOT NULL
);

ALTER TABLE document_files
    ADD CONSTRAINT uc_document_files_files UNIQUE (files_id);

ALTER TABLE document
    ADD CONSTRAINT FK_DOCUMENT_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES _user (id);

ALTER TABLE document
    ADD CONSTRAINT FK_DOCUMENT_ON_OBSLEDOVANIE FOREIGN KEY (obsledovanie_id) REFERENCES obsledovanie (id);

ALTER TABLE document
    ADD CONSTRAINT FK_DOCUMENT_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organizations (id);

ALTER TABLE document_files
    ADD CONSTRAINT fk_docfil_on_document FOREIGN KEY (document_id) REFERENCES document (id);

ALTER TABLE document_files
    ADD CONSTRAINT fk_docfil_on_upload_file FOREIGN KEY (files_id) REFERENCES upload_file (id);

INSERT INTO public.document (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, birth_date, date_analiza, description, name, patronymic, pin, sex, surname, zaklyuchenie, doctor_id, obsledovanie_id, organization_id) VALUES ('06a14af3-fbc2-410b-bcfd-f28ea61e0620', 'string', '2023-11-21 12:51:34.362000', false, null, null, 'string', '2023-11-21 12:51:34.362000', '2023-11-21 00:00:00.000000', '2023-11-21 00:00:00.000000', 'string', 'prikol', 'string', 'string', 1, 'string', 'string', 2, 1, 1);
