package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateEtatDto;
import com.mns.cda.locmns.dto.UpdateEtatDto;
import com.mns.cda.locmns.model.Etat;
import com.mns.cda.locmns.service.EtatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/etat")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les etat")
public class EtatController {

    private final EtatService service;

    @GetMapping("/list")
    public List<Etat> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etat> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Etat> create(
            @RequestBody @Valid CreateEtatDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateEtatDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
