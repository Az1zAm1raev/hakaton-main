CREATE TABLE organizations
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted            BOOLEAN                                 NOT NULL,
    deleted_by         VARCHAR(255),
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,

    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    address            VARCHAR(255),
    oz_full_name       TEXT,

    oz_short_name      VARCHAR(255),
    CONSTRAINT pk_organizations PRIMARY KEY (id)
);

INSERT INTO public.organizations (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, address, oz_full_name, oz_short_name) VALUES (1, 'string', '2023-06-17 20:57:03.154000', false, null, null, 'string', '2023-06-17 20:57:03.154000', 'string', 'Пускай Мэгумин и Юн-юн — невероятные представительницы Багрового клана, им всё равно многому ещё нужно научиться, ведь совершенству нет предела. Юн-юн принялась изучать продвинутую магию, а Мэгумин пошла по', '2003-05-31 00:00:00.000000');
