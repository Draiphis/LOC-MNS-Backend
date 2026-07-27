package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CreateMaterielDto;
import com.mns.cda.locmns.dto.DisponibiliteModeleDto;
import com.mns.cda.locmns.dto.MaterielDisponibleDto;
import com.mns.cda.locmns.dto.UpdateMaterielDto;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.service.MaterielService;
import com.mns.cda.locmns.view.MaterielView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/materiel")
@RequiredArgsConstructor
@Tag(name = "Matériels", description = "Gestion des exemplaires physiques du catalogue")
public class MaterielController {

    private final MaterielService service;

    @GetMapping("/list")
    @JsonView(MaterielView.class )
    @Operation(summary = "Lister les matériels")
    @ApiResponse(responseCode = "200", description = "Liste des matériels")
    public List<Materiel> getAll() {
        return service.getAll();
    }

    @GetMapping("/disponibles/{modeleId}")
    @Operation(summary = "Lister les matériels disponibles d'un modèle")
    @ApiResponse(responseCode = "200", description = "Liste des matériels disponibles")
    public List<MaterielDisponibleDto> getDisponibles(
            @Parameter(description = "Identifiant du modèle", example = "1")
            @PathVariable int modeleId) {
        return service.getDisponibles(modeleId);
    }

    @GetMapping("/disponibilite-modele/{modeleId}")
    @Operation(
            summary = "Consulter la première disponibilité d'un modèle",
            description = "Indique si le modèle est réservable et la première date de début autorisée."
    )
    @ApiResponse(responseCode = "200", description = "Disponibilité du modèle")
    public DisponibiliteModeleDto getDisponibiliteModele(
            @Parameter(description = "Identifiant du modèle", example = "1")
            @PathVariable int modeleId) {
        return service.getDisponibiliteModele(modeleId);
    }

    @GetMapping("/{id}")
    @JsonView(MaterielView.class )
    @Operation(summary = "Consulter un matériel")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matériel trouvé"),
            @ApiResponse(responseCode = "404", description = "Matériel introuvable")
    })
    public ResponseEntity<Materiel> get(
            @Parameter(description = "Identifiant du matériel", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer un matériel")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Matériel créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "409", description = "Référence déjà utilisée")
    })
    public ResponseEntity<Materiel> create(
            @RequestBody @Valid CreateMaterielDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier un matériel")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Matériel modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Matériel introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant du matériel", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateMaterielDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un matériel")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Matériel supprimé"),
            @ApiResponse(responseCode = "404", description = "Matériel introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant du matériel", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
