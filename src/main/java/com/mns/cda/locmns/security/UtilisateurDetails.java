package com.mns.cda.locmns.security;

import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class UtilisateurDetails implements UserDetails {

    protected Utilisateur utilisateur;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> listeAuthority = new ArrayList<>();
        for(Role role : this.utilisateur .getRoles()){
            listeAuthority.add( new SimpleGrantedAuthority("ROLE_" + role.getRole().name()));
        }
        return listeAuthority;
    }

    @Override
    public @Nullable String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
