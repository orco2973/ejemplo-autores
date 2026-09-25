package com.ariel.ejemplo.autor.domain;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Autor {

    private final UUID id;
    private final NombreAutor nombre;
    private final EmailAutor email;

    public Autor(UUID id, NombreAutor nombre, EmailAutor email){
        if (id == null) throw new IllegalArgumentException("El id del autor es obligatorio");
        if (nombre == null) throw new IllegalArgumentException("El nombre del autor es obligatorio");
        if (email == null) throw new IllegalArgumentException("El email del autor es obligatorio");
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
}
