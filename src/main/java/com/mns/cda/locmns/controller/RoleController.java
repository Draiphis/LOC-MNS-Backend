package com.mns.cda.locmns.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.service.RoleService;
import com.mns.cda.locmns.view.RoleView;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/role")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les role")
public class RoleController {

    private final RoleService service;

    @GetMapping("/list")
    @JsonView(RoleView.class)
    public List<Role> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @JsonView(RoleView.class)
    public ResponseEntity<Role> get(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
