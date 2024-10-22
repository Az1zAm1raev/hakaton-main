package com.example.hakaton.exception;

import org.springframework.http.HttpStatus;

public enum CustomError {
    //region User
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),

    USER_NOT_CREATED("User not create", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_NOT_UPDATED("User not update", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_NOT_DELETED("User not delete", HttpStatus.UNPROCESSABLE_ENTITY),
    USER_DUPLICATE_PIN("User with this pin already exist", HttpStatus.CONFLICT),
    USER_NOT_AUTHENTICATE("Invalid data", HttpStatus.BAD_REQUEST),
    USER_NOT_HAVE_ANY_ORGANISATION("User not have any organisation", HttpStatus.UNPROCESSABLE_ENTITY),
    //endregion
    //region Organisation
    ORGANISATION_NOT_FOUND("Organisation not found", HttpStatus.NOT_FOUND),
    ORGANISATION_NOT_CREATED("Organisation not create", HttpStatus.UNPROCESSABLE_ENTITY),
    ORGANISATION_NOT_UPDATED("Organisation not update", HttpStatus.UNPROCESSABLE_ENTITY),
    ORGANISATION_NOT_DELETED("Organisation not delete", HttpStatus.UNPROCESSABLE_ENTITY),
    //endregion
    //region Organisation
    ROLE_NOT_FOUND("Role not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_CREATED("Role not create", HttpStatus.UNPROCESSABLE_ENTITY),
    ROLE_NOT_UPDATED("Role not update", HttpStatus.UNPROCESSABLE_ENTITY),
    ROLE_NOT_DELETED("Role not delete", HttpStatus.UNPROCESSABLE_ENTITY),
    //endregion
    //region Document
    DOCUMENT_NOT_FOUND("Document not found", HttpStatus.NOT_FOUND),
    DOCUMENT_NOT_CREATED("Document not create", HttpStatus.UNPROCESSABLE_ENTITY),
    DOCUMENT_NOT_UPDATED("Document not update", HttpStatus.UNPROCESSABLE_ENTITY),
    DOCUMENT_NOT_DELETED("Document not delete", HttpStatus.UNPROCESSABLE_ENTITY),

    BLANK_NOT_FOUND("Blank not found", HttpStatus.NOT_FOUND),
    BLANK_NOT_CREATED("Blank not create", HttpStatus.UNPROCESSABLE_ENTITY),
    BLANK_NOT_UPDATED("Blank not update", HttpStatus.UNPROCESSABLE_ENTITY),
    BLANK_NOT_DELETED("Blank not delete", HttpStatus.UNPROCESSABLE_ENTITY),


    OBSLEDOVANIE_NOT_FOUND("Obsledovanie not found", HttpStatus.NOT_FOUND),
    OBSLEDOVANIE_NOT_CREATED("Obsledovanie not create", HttpStatus.UNPROCESSABLE_ENTITY),
    OBSLEDOVANIE_NOT_UPDATED("Obsledovanie not update", HttpStatus.UNPROCESSABLE_ENTITY),
    OBSLEDOVANIE_NOT_DELETED("Obsledovanie not delete", HttpStatus.UNPROCESSABLE_ENTITY),




    //endregion
    //region Files
    FILE_NOT_FOUND("File not found", HttpStatus.NOT_FOUND),
    FILE_NOT_CREATED("File not create", HttpStatus.UNPROCESSABLE_ENTITY),
    SELECT_NO_FILES("Please select a file!", HttpStatus.BAD_REQUEST),
    FILE_TOO_LARGE("File size is too large, must be no more than 1048576 bytes", HttpStatus.BAD_REQUEST),
    FILE_TYPE_NOT_PICTURE("File type not picture", HttpStatus.BAD_REQUEST),
    //endregion
    //region Application
    NO_ACCESS("User no have access", HttpStatus.FORBIDDEN),
    NOT_AUTHENTICATE("User no authenticated", HttpStatus.UNAUTHORIZED),
    PATIENT_NOT_FOUND("Patient with this pin not found", HttpStatus.NOT_FOUND),
    //endregion
    ;
    private final String message;
    private final HttpStatus status;

    CustomError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
