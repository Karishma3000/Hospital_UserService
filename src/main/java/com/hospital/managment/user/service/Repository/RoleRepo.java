package com.hospital.managment.user.service.Repository;

import com.hospital.managment.user.service.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepo  extends JpaRepository<Role, Long> {
    public Optional<Role> findByRolename(String name);

}
