package com.hospital.managment.user.service.ServiceImpl;

import com.hospital.managment.user.service.Entity.User;
import com.hospital.managment.user.service.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CostomUserDetailsService implements  UserDetailsService {
  @Autowired
  private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from database
        User user=userRepo.findByUsername(username).orElseThrow(()->new RuntimeException("UserNot Found"));
        System.out.println(user);
        return user;
    }

}
