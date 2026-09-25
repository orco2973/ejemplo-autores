package com.ariel.ejemplo.autor.application;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.EmailAutor;
import com.ariel.ejemplo.autor.domain.NombreAutor;
import org.mapstruct.Mapper;

@Mapper
public interface AutorCreatorUseCaseMapper {

    Autor toDomain(AutorCreatorCommand command);

    AutorCreatorResult toResult(Autor autor);

    default NombreAutor toNombreAutor(String valor) {
        return new NombreAutor(valor);
    }

    default EmailAutor toEmailAutor(String valor) {
        return new EmailAutor(valor);
    }
}
