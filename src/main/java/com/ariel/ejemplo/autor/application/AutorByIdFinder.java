package com.ariel.ejemplo.autor.application;

public interface AutorByIdFinder {
    AutorByIdFinderResult perform(AutorByIdFinderCommand command);
}

