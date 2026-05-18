package com.mns.cda.locmns.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mns.cda.locmns.dto.ConnexionUtilisateurDto;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.service.UtilisateurService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthentificationController {

    private final UtilisateurService utilisateurService;
    private final AuthenticationProvider authenticationProvider;


    @PostMapping("/inscription")
    public ResponseEntity<CreateUtilisateurDto> inscription(
            @RequestBody CreateUtilisateurDto utilisateurDto){
        utilisateurService.create(utilisateurDto);

        return new ResponseEntity<>(utilisateurDto, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(
            @RequestBody ConnexionUtilisateurDto utilisateur){

        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(utilisateur.getEmail(), utilisateur.getPassword()));

            String jwt = Jwts.builder()
                    .setSubject(utilisateur.getEmail())
                    .signWith(SignatureAlgorithm.HS256, "azerty")
                    .compact();
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (AuthenticationException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

}
