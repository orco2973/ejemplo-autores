package com.ariel.ejemplo.autor.domain;

public record EmailAutor(String valor) {

    public EmailAutor {
        if (valor == null || !valor.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("El email del autor no es valido");
        }
    }
}

