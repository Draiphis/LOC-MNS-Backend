package com.mns.cda.locmns.service;

import com.mns.cda.locmns.dao.RoleDao;
import com.mns.cda.locmns.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleDao roleDao;


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
