package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.service.UtilisateurService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les utilisateur")
public class UtilisateurController {

    private final UtilisateurService service;

    @GetMapping("/list")
    public List<Utilisateur> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Utilisateur> create(
            @RequestBody @Valid CreateUtilisateurDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateUtilisateurDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
