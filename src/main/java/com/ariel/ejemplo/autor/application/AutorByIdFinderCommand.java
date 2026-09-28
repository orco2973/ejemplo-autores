package com.ariel.ejemplo.autor.application;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AutorByIdFinderCommand {

    private UUID id;
}

