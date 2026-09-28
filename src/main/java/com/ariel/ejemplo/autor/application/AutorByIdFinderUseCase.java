package com.ariel.ejemplo.autor.application;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.AutorByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutorByIdFinderUseCase implements AutorByIdFinder{

    private final AutorByIdFinderOutputPort outputPort;

    private AutorByIdFinderUseCaseMapper mapper;


    @Override
    public AutorByIdFinderResult perform(AutorByIdFinderCommand command) {

        log.info("Buscando Autor id={}", command.getId());

        Autor autor = outputPort.perform(command.getId());

        return mapper.toResult(autor);
    }
}
