package com.ariel.ejemplo.autor.application;

public interface AutorCreator {
    AutorCreatorResult perform(AutorCreatorCommand command);
}
