package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateModeleDto;
import com.mns.cda.locmns.dto.UpdateModeleDto;
import com.mns.cda.locmns.model.Modele;
import com.mns.cda.locmns.service.ModeleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Modele> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modele> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Modele> create(
            @RequestBody @Valid CreateModeleDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateModeleDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
