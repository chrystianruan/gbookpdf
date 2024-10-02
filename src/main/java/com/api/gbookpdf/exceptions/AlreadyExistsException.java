package com.api.gbookpdf.exceptions;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException(String model) {
        super(model+" já existe");
    }
}
