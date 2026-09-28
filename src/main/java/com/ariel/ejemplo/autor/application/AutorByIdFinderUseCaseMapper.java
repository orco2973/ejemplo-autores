package com.ariel.ejemplo.autor.application;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.EmailAutor;
import com.ariel.ejemplo.autor.domain.NombreAutor;
import org.mapstruct.Mapper;

@Mapper
public interface AutorByIdFinderUseCaseMapper {

    AutorByIdFinderResult toResult(Autor autor);

    default String toNombre(NombreAutor nombre) {
        return nombre.valor();
    }

    default String toEmail(EmailAutor email) {
        return email.valor();
    }


}
