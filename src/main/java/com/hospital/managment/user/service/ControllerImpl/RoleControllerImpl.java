package com.hospital.managment.user.service.ControllerImpl;

import com.hospital.managment.user.service.Controller.RoleController;
import com.hospital.managment.user.service.Entity.Role;
import com.hospital.managment.user.service.ServiceImpl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleControllerImpl implements RoleController {
    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Override
    public ResponseEntity<String> addRole(@RequestBody Role role) {
        try {
            String serviceReturn =roleServiceImpl.addRole(role);
            return new ResponseEntity<>(serviceReturn, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Something went wrong",HttpStatus.BAD_REQUEST);
        }
    }
}
