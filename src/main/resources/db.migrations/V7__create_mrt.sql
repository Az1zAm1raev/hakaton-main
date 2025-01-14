CREATE TABLE mrt
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    code               VARCHAR(255),
    name               VARCHAR(255),
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted            BOOLEAN                                 NOT NULL,
    deleted_by         VARCHAR(255),
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,


    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_mrt PRIMARY KEY (id)
);


INSERT INTO public.mrt (id, code, name, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date) VALUES (1, 'string', 'string', 'string', '2023-11-15 14:50:55.167000', false, null, null, 'string', '2023-11-15 14:50:55.167000');
