package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateDocumentationDto;
import com.mns.cda.locmns.dto.UpdateDocumentationDto;
import com.mns.cda.locmns.model.Documentation;
import com.mns.cda.locmns.service.DocumentationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/documentation")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les documentation")
public class DocumentationController {

    private final DocumentationService service;

    @GetMapping("/list")
    public List<Documentation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentation> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Documentation> create(
            @RequestBody @Valid CreateDocumentationDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateDocumentationDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
