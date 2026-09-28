package com.ariel.ejemplo.autor.infrastructure.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorByIdFinderResponse {

    private UUID id;

    private String nombre;

    private String email;
}
