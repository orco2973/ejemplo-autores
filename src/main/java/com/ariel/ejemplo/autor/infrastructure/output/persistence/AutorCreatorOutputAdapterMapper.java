package com.ariel.ejemplo.autor.infrastructure.output.persistence;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.EmailAutor;
import com.ariel.ejemplo.autor.domain.NombreAutor;
import org.mapstruct.Mapper;

import javax.swing.text.html.parser.Entity;

@Mapper
public interface AutorCreatorOutputAdapterMapper {

    AutorEntity toEntity(Autor autor);

    default String fromNombreAutor(NombreAutor nombre) {
        return nombre.valor();
    }

    default String fromEmailAutor(EmailAutor email) {
        return email.valor();
    }
}
