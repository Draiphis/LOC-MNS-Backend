package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CatalogueModeleDto;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.model.RoleNom;
import com.mns.cda.locmns.security.IsAdmin;
import com.mns.cda.locmns.security.IsUser;
import com.mns.cda.locmns.security.UtilisateurDetails;
import com.mns.cda.locmns.service.ModeleService;
import com.mns.cda.locmns.view.ModeleView;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/modele")
@RequiredArgsConstructor
@Tag(name = "Modèles", description = "Catalogue et gestion des modèles de matériel")
@SecurityRequirement(name = "bearerAuth")
public class ModeleController {

    private final ModeleService service;

    @GetMapping("/list")
    @IsUser
    @Operation(
            summary = "Rechercher les modèles du catalogue",
            description = "Retourne le catalogue avec le stock disponible et des filtres facultatifs."
    )
    @ApiResponse(responseCode = "200", description = "Catalogue filtré")
    public List<CatalogueModeleDto> getAll(
            @Parameter(description = "Nom du type à rechercher", example = "Ordinateur")
            @RequestParam(required = false) String type,
            @Parameter(description = "Nom de la marque à rechercher", example = "Dell")
            @RequestParam(required = false) String marque,
            @Parameter(description = "Limiter aux modèles disponibles", example = "true")
            @RequestParam(required = false) Boolean disponible
    ) {
        return service.getCatalogue(type,marque,disponible);
    }


    @GetMapping("/{id}")
    @JsonView(ModeleView.class )
    @IsUser
    @Operation(summary = "Consulter un modèle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modèle trouvé"),
            @ApiResponse(responseCode = "404", description = "Modèle introuvable")
    })
    public ResponseEntity<Modele> get(
            @Parameter(description = "Identifiant du modèle", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @IsAdmin
    @Operation(summary = "Créer un modèle", description = "Opération réservée aux administrateurs.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Modèle créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Droits administrateur requis")
    })
    public ResponseEntity<Modele> create(
            @RequestBody @Valid CreateModeleDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @IsAdmin
    @Operation(summary = "Modifier un modèle", description = "Opération réservée aux administrateurs.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Modèle modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Droits administrateur requis"),
            @ApiResponse(responseCode = "404", description = "Modèle introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant du modèle", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateModeleDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @IsAdmin
    @Operation(summary = "Supprimer un modèle", description = "Opération réservée aux administrateurs.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Modèle supprimé"),
            @ApiResponse(responseCode = "403", description = "Droits administrateur requis"),
            @ApiResponse(responseCode = "404", description = "Modèle introuvable")
    })
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UtilisateurDetails utilisateurDetails,
            @Parameter(description = "Identifiant du modèle", example = "1")
            @PathVariable int id) {

        if(utilisateurDetails.getUtilisateur().getRoles().stream().noneMatch(role -> role.getRole() == RoleNom.ADMIN)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
