package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateEtatDto;
import com.mns.cda.locmns.dto.UpdateEtatDto;
import com.mns.cda.locmns.model.Etat;
import com.mns.cda.locmns.service.EtatService;
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
@RequestMapping("/etat")
@RequiredArgsConstructor
@Tag(name = "États", description = "Gestion des états d'usure du matériel")
public class EtatController {

    private final EtatService service;

    @GetMapping("/list")
    @Operation(summary = "Lister les états")
    @ApiResponse(responseCode = "200", description = "Liste des états")
    public List<Etat> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter un état")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "État trouvé"),
            @ApiResponse(responseCode = "404", description = "État introuvable")
    })
    public ResponseEntity<Etat> get(
            @Parameter(description = "Identifiant de l'état", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer un état")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "État créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Etat> create(
            @RequestBody @Valid CreateEtatDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier un état")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "État modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "État introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant de l'état", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateEtatDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un état")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "État supprimé"),
            @ApiResponse(responseCode = "404", description = "État introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de l'état", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
