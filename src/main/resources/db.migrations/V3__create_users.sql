CREATE TABLE _user
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_by         VARCHAR(255),
    created_date       TIMESTAMP WITHOUT TIME ZONE,
    deleted            BOOLEAN                                 NOT NULL,
    deleted_by         VARCHAR(255),
    deleted_date       TIMESTAMP WITHOUT TIME ZONE,

    last_modified_by   VARCHAR(255),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    email              VARCHAR(255),
    name               VARCHAR(255)                            NOT NULL,
    password           VARCHAR(255)                            NOT NULL,
    patronymic         VARCHAR(255),
    phone              VARCHAR(255),
    pin                VARCHAR(255)                            NOT NULL,
    surname            VARCHAR(255)                            NOT NULL,

    CONSTRAINT pk__user PRIMARY KEY (id)
);

CREATE TABLE _user_organization
(
    _user_id        BIGINT NOT NULL,
    organization_id BIGINT NOT NULL
);

CREATE TABLE _user_role
(
    _user_id BIGINT NOT NULL,
    role_id  BIGINT NOT NULL
);

ALTER TABLE _user
    ADD CONSTRAINT uc__user_email UNIQUE (email);

ALTER TABLE _user
    ADD CONSTRAINT uc__user_pin UNIQUE (pin);

ALTER TABLE _user_organization
    ADD CONSTRAINT fk_useorg_on_organization FOREIGN KEY (organization_id) REFERENCES organizations (id);

ALTER TABLE _user_organization
    ADD CONSTRAINT fk_useorg_on_user FOREIGN KEY (_user_id) REFERENCES _user (id);

ALTER TABLE _user_role
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE _user_role
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (_user_id) REFERENCES _user (id);


INSERT INTO public._user (id, created_by, created_date, deleted, deleted_by, deleted_date, last_modified_by, last_modified_date, email, name, password, patronymic, phone, pin, surname) VALUES (2, null, null, false, null, null, null, null, 'string', 'string', '$2a$12$JLhuYuqgDVbvftNeBExv/ersDltYSW90MLPVgqjfl.6FnNXNPXem.', 'string', 'string', 'string', 'string');
INSERT INTO public._user_organization (_user_id, organization_id) VALUES (2, 1);
INSERT INTO public._user_role (_user_id, role_id) VALUES (2, 1);
