package com.ariel.ejemplo.autor.domain;

import java.util.UUID;

public interface AutorByIdFinderOutputPort {

    Autor perform(UUID id);

}
