package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.security.IsUser;
import com.mns.cda.locmns.service.UtilisateurService;
import com.mns.cda.locmns.view.UtilisateurView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@IsUser
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Consultation et gestion des utilisateurs")
@SecurityRequirement(name = "bearerAuth")
public class UtilisateurController {

    private final UtilisateurService service;

    @GetMapping("/list")
    @JsonView(UtilisateurView.class)
    @Operation(summary = "Lister les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs")
    public List<Utilisateur> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(UtilisateurView.class)
    @Operation(summary = "Consulter un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    public ResponseEntity<Utilisateur> get(
            @Parameter(description = "Identifiant de l'utilisateur", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Adresse e-mail déjà utilisée")
    })
    public ResponseEntity<Utilisateur> create(
            @RequestBody @Valid CreateUtilisateurDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant de l'utilisateur", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateUtilisateurDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un utilisateur")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé"),
            @ApiResponse(responseCode = "404", description = "Utilisateur introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de l'utilisateur", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
