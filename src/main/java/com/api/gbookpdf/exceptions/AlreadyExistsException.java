package com.api.gbookpdf.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends CustomException {
    private static final HttpStatus status = HttpStatus.FORBIDDEN;
    public AlreadyExistsException(String model) {
        super(model+" já existe", status);
    }
}
