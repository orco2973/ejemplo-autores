package com.ariel.ejemplo.autor.domain;

public class AutorNotFoundException extends RuntimeException {

    public AutorNotFoundException(String message) {
        super(message);
    }
}
