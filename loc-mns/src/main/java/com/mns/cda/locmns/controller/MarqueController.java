package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateMarqueDto;
import com.mns.cda.locmns.dto.UpdateMarqueDto;
import com.mns.cda.locmns.model.Marque;
import com.mns.cda.locmns.service.MarqueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/marque")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les marque")
public class MarqueController {

    private final MarqueService service;

    @GetMapping("/list")
    public List<Marque> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marque> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Marque> create(
            @RequestBody @Valid CreateMarqueDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateMarqueDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
