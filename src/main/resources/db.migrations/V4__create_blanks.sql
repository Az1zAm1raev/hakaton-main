CREATE TABLE blank
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
    name               VARCHAR(255),
    name_analiza       VARCHAR(255),
    patronymic         VARCHAR(255),

    pin                VARCHAR(255),
    result             VARCHAR(255),

    surname            VARCHAR(255),
    type_analiza       VARCHAR(255),
    doctor_id          BIGINT,
    organization_id    BIGINT,

    CONSTRAINT pk_blank PRIMARY KEY (id)
);

ALTER TABLE blank
    ADD CONSTRAINT FK_BLANK_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES _user (id);

ALTER TABLE blank
    ADD CONSTRAINT FK_BLANK_ON_ORGANIZATION FOREIGN KEY (organization_id) REFERENCES organizations (id);

INSERT INTO public.blank (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, birth_date, date_analiza, name, name_analiza, patronymic, pin, result, surname, type_analiza, doctor_id, organization_id) VALUES ('0709968e-85f7-4fd1-b760-1f012bea0b5c', 'string', '2023-06-18 09:37:56.177000', false, null, null, 'string', '2023-06-18 09:37:56.177000', '2023-06-18 00:00:00.000000', '2023-06-18 00:00:00.000000', 'string', 'Blank_string', 'string', 'string', 'string', 'string', 'string', 2, 1);
INSERT INTO public.blank (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, birth_date, date_analiza, name, name_analiza, patronymic, pin, result, surname, type_analiza, doctor_id, organization_id) VALUES ('70281845-7f03-4264-b896-a52abb6f920d', 'string', '2023-06-18 09:38:41.211000', false, null, null, 'string', '2023-06-18 09:38:41.211000', '2023-06-18 00:00:00.000000', '2023-06-18 00:00:00.000000', 'kama', 'Blank_kama', 'string', 'string', 'string', 'string', 'string', 2, 1);
INSERT INTO public.blank (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, birth_date, date_analiza, name, name_analiza, patronymic, pin, result, surname, type_analiza, doctor_id, organization_id) VALUES ('15d18136-ddb0-439f-ad5e-8427e1e8bfc2', 'string', '2023-10-07 11:51:51.656000', false, null, null, 'string', '2023-10-07 11:51:51.656000', '2023-10-07 00:00:00.000000', '2023-10-07 00:00:00.000000', 'strisdvng', 'Blank_strisdvng', 'string', 'string', 'string', 'string', 'string', 2, 1);
