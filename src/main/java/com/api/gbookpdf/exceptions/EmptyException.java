package com.api.gbookpdf.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter @Setter
public class EmptyException extends CustomException {
    private static final long serialVersionUID = 1149241039409861914L;
    private static final HttpStatus status = HttpStatus.NOT_FOUND;
    public EmptyException(String message) {
        super(message+" não encontrado", status);
    }

}
