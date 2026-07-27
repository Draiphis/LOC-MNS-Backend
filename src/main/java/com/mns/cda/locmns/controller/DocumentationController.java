package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateDocumentationDto;
import com.mns.cda.locmns.dto.UpdateDocumentationDto;
import com.mns.cda.locmns.model.Documentation;
import com.mns.cda.locmns.service.DocumentationService;
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
@RequestMapping("/documentation")
@RequiredArgsConstructor
@Tag(name = "Documentations", description = "Gestion des documents associés au matériel")
public class DocumentationController {

    private final DocumentationService service;

    @GetMapping("/list")
    @Operation(summary = "Lister les documentations")
    @ApiResponse(responseCode = "200", description = "Liste des documentations")
    public List<Documentation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter une documentation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documentation trouvée"),
            @ApiResponse(responseCode = "404", description = "Documentation introuvable")
    })
    public ResponseEntity<Documentation> get(
            @Parameter(description = "Identifiant de la documentation", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Créer une documentation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Documentation créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<Documentation> create(
            @RequestBody @Valid CreateDocumentationDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @Operation(summary = "Modifier une documentation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Documentation modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Documentation introuvable")
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identifiant de la documentation", example = "1")
            @PathVariable int id,
            @RequestBody @Valid UpdateDocumentationDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer une documentation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Documentation supprimée"),
            @ApiResponse(responseCode = "404", description = "Documentation introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant de la documentation", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
