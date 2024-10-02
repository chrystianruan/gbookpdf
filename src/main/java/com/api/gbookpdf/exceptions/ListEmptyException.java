package com.api.gbookpdf.exceptions;

public class ListEmptyException extends Exception{
    public ListEmptyException(String model) {
        super("Nenhum(a) "+ model +" foi encontrado(a)");
    }
}
