package com.mns.cda.locmns.security;

import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.model.Utilisateur;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UtilisateurDetailsService implements UserDetailsService {

    protected final UtilisateurDao utilisateurDao;

    @Override
    public UtilisateurDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findByEmailWithRoles(email);

        if (optionalUtilisateur.isEmpty()){
            throw new UsernameNotFoundException(email);
        }

        return new UtilisateurDetails(optionalUtilisateur.get());
    }
}
