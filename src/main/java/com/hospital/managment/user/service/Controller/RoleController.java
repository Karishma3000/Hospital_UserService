package com.hospital.managment.user.service.Controller;

import com.hospital.managment.user.service.Entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface RoleController {
    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestBody Role role);
}
