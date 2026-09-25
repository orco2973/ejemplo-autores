package com.ariel.ejemplo.autor.infrastructure.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorCreatorResponse {

    private UUID id;

}
