package acikgoz.kaan.UserSecurityAPI.service;

import acikgoz.kaan.UserSecurityAPI.entity.Role;
import acikgoz.kaan.UserSecurityAPI.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired RoleRepository roleRepository;

    public Role findOrCreateRole(Role.RoleType roleType) {
        return roleRepository.findByType(roleType).orElseGet(() -> {
            // If the role doesn't exist, create and save it
            Role newRole = new Role();
            newRole.setType(roleType);
            return roleRepository.save(newRole);
        });
    }

}
