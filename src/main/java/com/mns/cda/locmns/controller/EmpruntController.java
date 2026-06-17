package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateEmpruntDto;
import com.mns.cda.locmns.dto.EmpruntReponseDto;
import com.mns.cda.locmns.dto.UpdateEmpruntDto;
import com.mns.cda.locmns.model.Emprunt;
import com.mns.cda.locmns.service.EmpruntService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/emprunt")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les emprunt")
public class EmpruntController {

    private final EmpruntService service;

    @GetMapping("/list")
    public List<EmpruntReponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprunt> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody @Valid CreateEmpruntDto dto) {

        Emprunt emprunt = service.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", emprunt.getId(),
                        "status", "created"
                )
        );
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateEmpruntDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<Void> valider(@PathVariable int id) {
        service.valider(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<Void> refuser(@PathVariable int id) {
        service.refuser(id);
        return ResponseEntity.noContent().build();
    }

}
