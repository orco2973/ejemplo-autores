package com.ariel.ejemplo.autor.infrastructure.input;

import com.ariel.ejemplo.autor.application.AutorByIdFinder;
import com.ariel.ejemplo.autor.application.AutorByIdFinderCommand;
import com.ariel.ejemplo.autor.application.AutorByIdFinderResult;
import com.ariel.ejemplo.autor.domain.AutorNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorByIdGetAdapter {

    private final AutorByIdFinder useCase;

    private final AutorByIdFinderAdapterMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<AutorByIdFinderResponse> perform(
            @PathVariable("id") UUID id) {

        log.info("GET /api/autores/{}", id);

        AutorByIdFinderCommand command = mapper.toCommand(id);
        AutorByIdFinderResult result = useCase.perform(command);
        AutorByIdFinderResponse response = mapper.toResponse(result);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(AutorNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFound(AutorNotFoundException e) {
        log.warn("Autor no encontrado: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorResponse> handleInvalidId(MethodArgumentTypeMismatchException e) {
        String message = "El id debe ser un UUID valido";
        log.warn("Id invalido: {}", e.getValue());
        return ResponseEntity.badRequest().body(new ErrorResponse(400, message));
    }
}
