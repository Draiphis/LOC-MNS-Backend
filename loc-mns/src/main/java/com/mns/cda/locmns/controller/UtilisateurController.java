package com.mns.cda.locmns.controller;


import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.model.Utilisateur;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="AppUser", description = "API pour manipuler les utilisateur")
public class UtilisateurController {

    private final UtilisateurDao utilisateur;


    @GetMapping("/list")
    @Operation(summary="Récupère tout les utilisateurs",
            description = "Récupère et affiche la liste de tout les utilisateurs et leurs données associés")
    public List<Utilisateur> getAll(){

        return utilisateur.findAll();

    }

}
