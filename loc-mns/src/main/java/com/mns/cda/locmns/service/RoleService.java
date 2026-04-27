package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.RoleDao;
import com.mns.cda.locmns.dto.CreateRoleDto;
import com.mns.cda.locmns.dto.UpdateRoleDto;
import com.mns.cda.locmns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleDao;

    // CREATE
    public Role create(CreateRoleDto dto) {
        Role u = new Role();
        
        u.setNom(dto.getNom());
        

        return roleDao.save(u);
    }

    // UPDATE (PUT)
    public void update(int id, UpdateRoleDto dto) {
        Role u = roleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Role non trouvé"));
        
        u.setNom(dto.getNom());
        roleDao.save(u);
    }

    // DELETE
    public void delete(int id) {
        if (!roleDao.existsById(id)) {
            throw new RuntimeException("Role non trouvé");
        }
        roleDao.deleteById(id);
    }

    // GET ONE
    public Role getById(int id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Role non trouvé"));
    }

    // GET ALL
    public java.util.List<Role> getAll() {
        return roleDao.findAll();
    }
}
