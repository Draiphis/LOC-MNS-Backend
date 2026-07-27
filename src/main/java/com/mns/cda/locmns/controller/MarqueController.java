package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CreateMarqueDto;
import com.mns.cda.locmns.dto.UpdateMarqueDto;
import com.mns.cda.locmns.model.Marque;
import com.mns.cda.locmns.service.MarqueService;
import com.mns.cda.locmns.view.MarqueView;
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
@RequestMapping("/marque")
@RequiredArgsConstructor
@Tag(name = "Marques", description = "Gestion des marques de matériel")
public class MarqueController {

    private final MarqueService service;

    @GetMapping("/list")
    @JsonView(MarqueView.class )
    @Operation(summary = "Lister les marques")
    @ApiResponse(responseCode = "200", description = "Liste des marques")
    public List<Marque> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(MarqueView.class )
    @Operation(summary = "Consulter une marque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Marque trouvée"),
            @ApiResponse(responseCode = "404", description = "Marque introuvable")
    })
    public ResponseEntity<Marque> get(
            @Parameter(description = "Identifiant de la marque", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer une marque")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Marque créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Marque> create(
            @RequestBody @Valid CreateMarqueDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier une marque")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Marque modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Marque introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant de la marque", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateMarqueDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer une marque")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Marque supprimée"),
            @ApiResponse(responseCode = "404", description = "Marque introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de la marque", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
