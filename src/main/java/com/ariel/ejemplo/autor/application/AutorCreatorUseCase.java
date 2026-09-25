package com.ariel.ejemplo.autor.application;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.AutorCreatorOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutorCreatorUseCase implements AutorCreator{

    private final AutorCreatorOutputPort outputPort;

    private final AutorCreatorUseCaseMapper mapper;

    @Override
    public AutorCreatorResult perform(AutorCreatorCommand command) {
        log.info("Creando Autor id={}", command.getId());
        Autor autor = mapper.toDomain(command);
        outputPort.perform(autor);
        log.info("Autor persistido id={}", autor.getId());
        return mapper.toResult(autor);



    }
}
