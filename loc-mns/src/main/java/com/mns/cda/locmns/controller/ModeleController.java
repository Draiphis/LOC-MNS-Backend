package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.security.IsAdmin;
import com.mns.cda.locmns.security.IsUser;
import com.mns.cda.locmns.service.ModeleService;
import com.mns.cda.locmns.view.ModeleView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/modele")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les modele")
public class ModeleController {

    private final ModeleService service;

    @GetMapping("/list")
    @JsonView(ModeleView.class )
    @IsUser
    public List<Modele> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(ModeleView.class )
    @IsUser
    public ResponseEntity<Modele> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    @IsAdmin
    public ResponseEntity<Modele> create(
            @RequestBody @Valid CreateModeleDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    @IsAdmin
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateModeleDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    @IsAdmin
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
