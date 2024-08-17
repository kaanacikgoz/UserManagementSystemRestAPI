package acikgoz.kaan.UserSecurityAPI.repository;

import acikgoz.kaan.UserSecurityAPI.entity.Role;
import acikgoz.kaan.UserSecurityAPI.entity.Role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByType(RoleType roleType);

}
