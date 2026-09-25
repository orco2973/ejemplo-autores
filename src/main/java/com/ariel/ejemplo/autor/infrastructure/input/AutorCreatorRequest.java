package com.ariel.ejemplo.autor.infrastructure.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutorCreatorRequest {
    @NotNull
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @Email
    private String email;
}
