package com.hospital.managment.user.service.ServiceImpl;

import com.hospital.managment.user.service.Entity.Role;
import com.hospital.managment.user.service.Entity.User;
import com.hospital.managment.user.service.Repository.RoleRepo;
import com.hospital.managment.user.service.Service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepo roleRepo;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Override
    public String addRole(Role role) {
        Optional<Role> existUser = roleRepo.findByRolename(role.getRolename());
        if (existUser.isPresent()) {
            logger.warn("Role already exist");
            return "Role already exist";
        } else {
            roleRepo.save(role);
            logger.info("User save Successfully");
            return "User save Successfully";
        }
    }
}