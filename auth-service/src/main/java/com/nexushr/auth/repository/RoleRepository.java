package com.nexushr.auth.repository;

import com.nexushr.auth.model.Role;
import com.nexushr.auth.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
