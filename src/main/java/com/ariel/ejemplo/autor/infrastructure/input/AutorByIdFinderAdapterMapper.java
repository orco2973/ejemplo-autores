package com.ariel.ejemplo.autor.infrastructure.input;

import com.ariel.ejemplo.autor.application.AutorByIdFinderCommand;
import com.ariel.ejemplo.autor.application.AutorByIdFinderResult;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface AutorByIdFinderAdapterMapper {

    default AutorByIdFinderCommand toCommand(UUID id){
        return new AutorByIdFinderCommand(id);
    }

    AutorByIdFinderResponse toResponse(AutorByIdFinderResult result);
}
