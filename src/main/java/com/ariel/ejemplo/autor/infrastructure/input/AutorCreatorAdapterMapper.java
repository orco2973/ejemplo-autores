package com.ariel.ejemplo.autor.infrastructure.input;

import com.ariel.ejemplo.autor.application.AutorCreatorCommand;
import com.ariel.ejemplo.autor.application.AutorCreatorResult;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper
public interface AutorCreatorAdapterMapper {

    AutorCreatorCommand toCommand(AutorCreatorRequest request);

    AutorCreatorResponse toResponse(AutorCreatorResult result);
}
