package com.ariel.ejemplo.autor.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AutorCreatorCommand {

    private UUID id;

    private String nombre;

    private String email;

}
