package com.hospital.managment.user.service.Service;

import com.hospital.managment.user.service.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    String signUp(User user);
    String otpVerficationLogin(String username ,String otp);
    String changePassword(String username, String oldPassword, String newPassword);
    String otpVerficationSignup(String username, String otp);

}
