package com.mns.cda.locmns.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateUtilisateurDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 36)
    private String password;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotNull
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeNaissance;
}
