package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.EmpruntReponseDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.service.EmpruntService;
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
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/emprunt")
@RequiredArgsConstructor
@Tag(name = "Emprunts", description = "Demandes, retours, validation et refus des emprunts")
public class EmpruntController {

    private final EmpruntService service;

    @GetMapping("/list")
    @Operation(summary = "Lister les emprunts")
    @ApiResponse(responseCode = "200", description = "Liste des emprunts")
    public List<EmpruntReponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/mes-emprunts")
    @Operation(
            summary = "Lister mes demandes et réservations",
            description = "Retourne uniquement les emprunts appartenant à l'utilisateur authentifié."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Demandes et réservations de l'utilisateur"),
            @ApiResponse(responseCode = "401", description = "Utilisateur non authentifié")
    })
    public List<EmpruntReponseDto> getMesEmprunts() {
        return service.getMesEmprunts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Emprunt trouvé"),
            @ApiResponse(responseCode = "404", description = "Emprunt introuvable")
    })
    public ResponseEntity<Emprunt> get(
            @Parameter(description = "Identifiant de l'emprunt", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Créer une demande d'emprunt",
            description = "Sélectionne un matériel disponible du modèle demandé et crée un emprunt EN_ATTENTE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Demande d'emprunt créée"),
            @ApiResponse(responseCode = "400", description = "Dates ou données invalides"),
            @ApiResponse(responseCode = "404", description = "Utilisateur ou matériel disponible introuvable")
    })
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid CreateEmpruntDto dto) {

        Emprunt emprunt = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", emprunt.getId(),
                        "status", "created"
                )
        );
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Enregistrer le retour réel d'un emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Emprunt modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Emprunt introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant de l'emprunt", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateEmpruntDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Emprunt supprimé"),
            @ApiResponse(responseCode = "404", description = "Emprunt introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de l'emprunt", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/valider")
    @Operation(summary = "Approuver une demande d'emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Emprunt approuvé"),
            @ApiResponse(responseCode = "404", description = "Emprunt introuvable")
    })
    public ResponseEntity<Void> valider(
            @Parameter(description = "Identifiant de l'emprunt", example = "1")
            @PathVariable int id) {
        service.valider(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/refuser")
    @Operation(summary = "Refuser une demande d'emprunt")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Emprunt refusé"),
            @ApiResponse(responseCode = "404", description = "Emprunt introuvable")
    })
    public ResponseEntity<Void> refuser(
            @Parameter(description = "Identifiant de l'emprunt", example = "1")
            @PathVariable int id) {
        service.refuser(id);
        return ResponseEntity.noContent().build();
    }

}
