package com.mns.cda.locmns.controller;

import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.security.IsAdmin;
import com.mns.cda.locmns.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@IsAdmin
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin/utilisateurs")
@RequiredArgsConstructor
@Tag(name = "Administration des utilisateurs", description = "Création de comptes par un administrateur")
@SecurityRequirement(name = "bearerAuth")
public class AdminUtilisateurController {

    private final UtilisateurService utilisateurService;

    @PostMapping
    @Operation(
            summary = "Créer un utilisateur",
            description = "Crée un compte avec le rôle utilisateur standard DEFAULT."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès réservé aux administrateurs"),
            @ApiResponse(responseCode = "409", description = "Adresse e-mail déjà utilisée")
    })
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody @Valid CreateUtilisateurDto dto) {
        Utilisateur utilisateur = utilisateurService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", utilisateur.getId(),
                        "status", "created"
                )
        );
    }
}
