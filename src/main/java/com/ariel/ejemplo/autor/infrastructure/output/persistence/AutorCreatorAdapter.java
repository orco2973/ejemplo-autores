package com.ariel.ejemplo.autor.infrastructure.output.persistence;

import com.ariel.ejemplo.autor.domain.Autor;
import com.ariel.ejemplo.autor.domain.AutorCreatorOutputPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutorCreatorAdapter implements AutorCreatorOutputPort {

    private final AutorRepository repository;

    private final AutorCreatorOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(Autor autor) {

        log.info("Guardando autor id={}", autor.getId());
        AutorEntity entity = mapper.toEntity(autor);
        repository.save(entity);
    }
}
