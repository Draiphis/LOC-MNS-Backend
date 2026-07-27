package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.service.RoleService;
import com.mns.cda.locmns.view.RoleView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name = "Rôles", description = "Consultation et suppression des rôles")
public class RoleController {

    private final RoleService service;

    @GetMapping("/list")
    @JsonView(RoleView.class)
    @Operation(summary = "Lister les rôles")
    @ApiResponse(responseCode = "200", description = "Liste des rôles")
    public List<Role> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(RoleView.class)
    @Operation(summary = "Consulter un rôle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rôle trouvé"),
            @ApiResponse(responseCode = "404", description = "Rôle introuvable")
    })
    public ResponseEntity<Role> get(
            @Parameter(description = "Identifiant du rôle", example = "1")
            @PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @DeleteMapping("delete/{id}")
    @Operation(summary = "Supprimer un rôle")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rôle supprimé"),
            @ApiResponse(responseCode = "404", description = "Rôle introuvable")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant du rôle", example = "1")
            @PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
