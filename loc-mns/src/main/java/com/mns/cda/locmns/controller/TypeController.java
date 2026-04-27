package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateTypeDto;
import com.mns.cda.locmns.dto.UpdateTypeDto;
import com.mns.cda.locmns.model.Type;
import com.mns.cda.locmns.service.TypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/type")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les type")
public class TypeController {

    private final TypeService service;

    @GetMapping("/list")
    public List<Type> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Type> create(
            @RequestBody @Valid CreateTypeDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateTypeDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
