package com.mns.cda.locmns.controller;

import com.mns.cda.locmns.dto.ConnexionUtilisateurDto;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.security.UtilisateurDetails;
import com.mns.cda.locmns.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Authentification", description = "Inscription et obtention d'un jeton JWT")
public class AuthentificationController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UtilisateurService utilisateurService;
    private final AuthenticationProvider authenticationProvider;


    @PostMapping("/inscription")
    @Operation(
            summary = "Inscrire un utilisateur",
            description = "Crée un nouveau compte auquel le rôle DEFAULT est attribué."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Utilisateur créé"),
            @ApiResponse(responseCode = "400", description = "Données d'inscription invalides"),
            @ApiResponse(responseCode = "409", description = "Adresse e-mail déjà utilisée")
    })
    public ResponseEntity<CreateUtilisateurDto> inscription(
            @RequestBody CreateUtilisateurDto utilisateurDto){
        utilisateurService.create(utilisateurDto);

        return new ResponseEntity<>(utilisateurDto, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    @Operation(
            summary = "Connecter un utilisateur",
            description = "Vérifie les identifiants et retourne un jeton JWT au format texte."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentification réussie ; JWT retourné"),
            @ApiResponse(responseCode = "401", description = "Identifiants incorrects")
    })
    public ResponseEntity<String> connexion(
            @RequestBody ConnexionUtilisateurDto utilisateur){

        try {
            UtilisateurDetails utilisateurDetails = (UtilisateurDetails) authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getEmail(), utilisateur.getPassword()))
                    .getPrincipal();

            String jwt = Jwts.builder()
                    .setSubject(utilisateur.getEmail())
                    .addClaims(Map.of("roles", utilisateurDetails.getUtilisateur().getRoles().stream()
                            .map(r->r.getRole().name()).collect(Collectors.joining(", "))))
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

}
