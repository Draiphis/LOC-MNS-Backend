package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateMaterielDto;
import com.mns.cda.locmns.dto.UpdateMaterielDto;
import com.mns.cda.locmns.model.Materiel;
import com.mns.cda.locmns.service.MaterielService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/materiel")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les materiel")
public class MaterielController {

    private final MaterielService service;

    @GetMapping("/list")
    public List<Materiel> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materiel> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Materiel> create(
            @RequestBody @Valid CreateMaterielDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateMaterielDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
