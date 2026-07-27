package com.mns.cda.locmns.unit.service;

import com.mns.cda.locmns.dao.RoleDao;
import com.mns.cda.locmns.dao.UtilisateurDao;
import com.mns.cda.locmns.dto.CreateUtilisateurDto;
import com.mns.cda.locmns.dto.UpdateUtilisateurDto;
import com.mns.cda.locmns.exception.EmailDejaUtiliseException;
import com.mns.cda.locmns.model.Role;
import com.mns.cda.locmns.model.RoleNom;
import com.mns.cda.locmns.model.Utilisateur;
import com.mns.cda.locmns.service.UtilisateurService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurDao utilisateurDao;

    @Mock
    private RoleDao roleDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @Test
    void devraitCreerUtilisateurAvecRoleParDefautEtMotDePasseEncode() {
        CreateUtilisateurDto dto = new CreateUtilisateurDto();
        dto.setEmail("test@mail.com");
        dto.setPassword("password");
        dto.setNom("Dupont");
        dto.setPrenom("Jean");
        dto.setDateDeNaissance(LocalDate.of(1995, 1, 1));

        Role role = new Role();
        role.setRole(RoleNom.DEFAULT);

        when(roleDao.findByRole(RoleNom.DEFAULT)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPwd");
        when(utilisateurDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.create(dto);

        assertNotNull(result);
        assertEquals("test@mail.com", result.getEmail());
        assertEquals("encodedPwd", result.getPassword());
        assertEquals(1, result.getRoles().size());

        verify(roleDao, times(1)).findByRole(RoleNom.DEFAULT);
        verify(utilisateurDao, times(1)).save(any(Utilisateur.class));
    }

    @Test
    void devraitRefuserUnEmailDejaUtilise() {
        CreateUtilisateurDto dto = new CreateUtilisateurDto();
        dto.setEmail("existant@mail.com");

        when(utilisateurDao.existsByEmail("existant@mail.com")).thenReturn(true);

        EmailDejaUtiliseException exception = assertThrows(
                EmailDejaUtiliseException.class,
                () -> utilisateurService.create(dto)
        );

        assertTrue(exception.getMessage().contains("existant@mail.com"));
        verify(utilisateurDao, never()).save(any());
        verify(roleDao, never()).findByRole(any());
    }

    @Test
    void devraitLeverExceptionSiRoleDefaultIntrouvable() {
        CreateUtilisateurDto dto = new CreateUtilisateurDto();
        dto.setPassword("password");

        when(roleDao.findByRole(RoleNom.DEFAULT)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> utilisateurService.create(dto));

        assertEquals("Role DEFAULT introuvable", ex.getMessage());
    }

    @Test
    void devraitMettreAJourUtilisateur() {
        UpdateUtilisateurDto dto = new UpdateUtilisateurDto();
        dto.setEmail("update@mail.com");
        dto.setPassword("newpass");
        dto.setNom("Nom");
        dto.setPrenom("Prenom");
        dto.setDateDeNaissance(LocalDate.of(2000, 1, 1));

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1);

        when(utilisateurDao.findById(1)).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");

        utilisateurService.update(1, dto);

        assertEquals("update@mail.com", utilisateur.getEmail());
        assertEquals("encodedNewPass", utilisateur.getPassword());

        verify(utilisateurDao, times(1)).save(utilisateur);
    }

    @Test
    void devraitLeverExceptionSiUtilisateurUpdateIntrouvable() {
        when(utilisateurDao.findById(1)).thenReturn(Optional.empty());

        UpdateUtilisateurDto dto = new UpdateUtilisateurDto();

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> utilisateurService.update(1, dto));

        assertEquals("Utilisateur non trouvé", ex.getMessage());
    }

    @Test
    void devraitSupprimerUtilisateurSiExiste() {
        when(utilisateurDao.existsById(1)).thenReturn(true);

        utilisateurService.delete(1);

        verify(utilisateurDao, times(1)).deleteById(1);
    }

    @Test
    void devraitLeverExceptionSiSuppressionUtilisateurInexistant() {
        when(utilisateurDao.existsById(1)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> utilisateurService.delete(1));

        assertEquals("Utilisateur non trouvé", ex.getMessage());
    }

    @Test
    void devraitRetournerUtilisateurParId() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1);

        when(utilisateurDao.findById(1)).thenReturn(Optional.of(utilisateur));

        Utilisateur result = utilisateurService.getById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void devraitLeverExceptionSiUtilisateurNonTrouve() {
        when(utilisateurDao.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> utilisateurService.getById(1));

        assertEquals("Utilisateur non trouvé", ex.getMessage());
    }

    @Test
    void devraitRetournerTousLesUtilisateurs() {
        when(utilisateurDao.findAll()).thenReturn(List.of(new Utilisateur(), new Utilisateur()));

        List<Utilisateur> result = utilisateurService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void devraitInsererUtilisateurAvecPasswordEncode() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPassword("raw");

        when(passwordEncoder.encode("raw")).thenReturn("encoded");
        when(utilisateurDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.insert(utilisateur);

        assertEquals("encoded", result.getPassword());
        verify(utilisateurDao, times(1)).save(utilisateur);
    }
}
