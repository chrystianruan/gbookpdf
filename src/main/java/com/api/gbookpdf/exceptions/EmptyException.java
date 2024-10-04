package com.api.gbookpdf.exceptions;

public class EmptyException extends Exception {
    private static final long serialVersionUID = 1149241039409861914L;
    public EmptyException(String message) {
        super(message+" não encontrado");
    }

}
