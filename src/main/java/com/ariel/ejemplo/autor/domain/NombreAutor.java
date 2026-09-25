package com.ariel.ejemplo.autor.domain;

public record NombreAutor(String valor) {

    public NombreAutor{
        if (valor == null || valor.isBlank()){
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if(valor.length() > 100){
            throw new IllegalArgumentException("El nombre del autor no puede superar los a 100 caracteres");
        }
    }
}
