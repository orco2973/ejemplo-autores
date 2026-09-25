package com.ariel.ejemplo.autor.infrastructure.output.persistence;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<AutorEntity, UUID> {
}
