package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CreateTypeDto;
import com.mns.cda.locmns.dto.StockParTypeDto;
import com.mns.cda.locmns.dto.UpdateTypeDto;
import com.mns.cda.locmns.model.Type;
import com.mns.cda.locmns.service.TypeService;
import com.mns.cda.locmns.view.TypeView;
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
@RequestMapping("/type")
@RequiredArgsConstructor
@Tag(name = "Types", description = "Gestion des catégories de matériel")
public class TypeController {

    private final TypeService service;

    @GetMapping("/list")
    @JsonView(TypeView.class )
    @Operation(summary = "Lister les types")
    @ApiResponse(responseCode = "200", description = "Liste des types")
    public List<Type> getAll() {
        return service.getAll();
    }

    @GetMapping("/stock")
    @Operation(summary = "Consulter le stock regroupé par type")
    @ApiResponse(responseCode = "200", description = "Quantités disponibles par type")
    public ResponseEntity<List<StockParTypeDto>> getStockParType() {
        return ResponseEntity.ok(service.getStockParType());
    }

    @GetMapping("/{id}")
    @JsonView(TypeView.class )
    @Operation(summary = "Consulter un type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Type trouvé"),
            @ApiResponse(responseCode = "404", description = "Type introuvable")
    })
    public ResponseEntity<Type> get(
            @Parameter(description = "Identifiant du type", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer un type")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Type créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Type> create(
            @RequestBody @Valid CreateTypeDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier un type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Type modifié"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Type introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant du type", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateTypeDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un type")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Type supprimé"),
            @ApiResponse(responseCode = "404", description = "Type introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant du type", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
