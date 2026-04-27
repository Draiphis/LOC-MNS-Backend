package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dto.CreateRoleDto;
import com.mns.cda.locmns.dto.UpdateRoleDto;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les role")
public class RoleController {

    private final RoleService service;

    @GetMapping("/list")
    public List<Role> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Role> create(
            @RequestBody @Valid CreateRoleDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestBody @Valid UpdateRoleDto dto) {

        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
