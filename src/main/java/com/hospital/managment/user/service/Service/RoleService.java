package com.hospital.managment.user.service.Service;

import com.hospital.managment.user.service.Entity.Role;
import com.hospital.managment.user.service.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    String addRole(Role role);
}
