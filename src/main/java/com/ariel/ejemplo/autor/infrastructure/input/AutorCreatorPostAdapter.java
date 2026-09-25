package com.ariel.ejemplo.autor.infrastructure.input;

import com.ariel.ejemplo.autor.application.AutorCreator;
import com.ariel.ejemplo.autor.application.AutorCreatorCommand;
import com.ariel.ejemplo.autor.application.AutorCreatorResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
public class AutorCreatorPostAdapter {

    private final AutorCreator useCase;
    private final AutorCreatorAdapterMapper mapper;

    @PostMapping
    public ResponseEntity<AutorCreatorResponse> perform(
            @Valid @RequestBody AutorCreatorRequest request){

        log.info("POST /api/autores id={}", request.getId());

        AutorCreatorCommand command = mapper.toCommand(request);
        AutorCreatorResult result = useCase.perform(command);
        AutorCreatorResponse response = mapper.toResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponse> handleInvalidRequest(MethodArgumentNotValidException e) {
        String message =
                e.getBindingResult().getFieldErrors().stream()
                        .map(error -> error.getField() + ": " + error.getDefaultMessage())
                        .sorted()
                        .collect(Collectors.joining(", "));
        log.warn("Request invalido: {}", message);
        return ResponseEntity.badRequest().body(new ErrorResponse(400, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        log.warn("Regla de negocio violada: {}", e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage()));
    }

}
