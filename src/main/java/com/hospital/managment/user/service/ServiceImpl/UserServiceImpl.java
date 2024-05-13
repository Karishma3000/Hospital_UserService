package com.hospital.managment.user.service.ServiceImpl;


import com.hospital.managment.user.service.Entity.Role;
import com.hospital.managment.user.service.Entity.User;
import com.hospital.managment.user.service.Repository.RoleRepo;
import com.hospital.managment.user.service.Repository.UserRepo;
import com.hospital.managment.user.service.Service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    Base64.Encoder encoder = Base64.getMimeEncoder();
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String signUp(User user) {
        Optional<User> existUser = userRepo.findByUsername(user.getUsername());
        if (existUser.isPresent()) {
            logger.warn("User already exist");
            return "User already exist";
        } else {
            List<Role> role = user.getRole();
            List<Role> getAllRole = roleRepo.findAll();
            for (Role n : role) {
                for (Role r : getAllRole) {
                    if (r.getRolename().equals(n.getRolename())) {
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                        String otp = generateOTP();
                        user.setOtp(otp);
                        sendEmail(user.getUsername(), "OTP verification mail", otp);
                        user.setCreationOtp(LocalDateTime.now());
                        user.setVerification(false);
                        List<Role> roleList = null;
                        roleList.add(r);
                        user.setRole(roleList);
                        userRepo.save(user);

                    }
                }
            }
            logger.info("User save Successfully");
            return "User save Successfully";
        }

    }

    public String generateOTP() {
        Random random = new Random();
        int otpValue = 1000000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    @Override
    public String otpVerficationLogin(String username, String otp) {
        Optional<User> existUser = userRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (existUser.get().getCreationOtp().isBefore(LocalDateTime.now()) && existUser.get().getCreationOtp().plusMinutes(5).isAfter(LocalDateTime.now())) {
                if (Objects.equals(existUser.get().getOtp(), otp)) {
                    return "loging successfully";
                } else {
                    return "otp is invalid";
                }
            } else {
                return "your tym for otp verification is out please login again";
            }
        } else {
            return "something went wrong please try again";
        }
    }

    @Override
    public String changePassword(String username, String oldPassword, String newPassword) {
        if (!(username == null && oldPassword == null && newPassword == null)) {
            Optional<User> existUser = userRepo.findByUsername(username);
            if (existUser.isPresent()) {
                if (Objects.equals(existUser.get().getPassword(), encoder.encodeToString(newPassword.getBytes()))) {
                    return "you cant keep password same as old password";
                }
                if (Objects.equals(existUser.get().getPassword(), encoder.encodeToString(oldPassword.getBytes()))) {
                    existUser.get().setPassword(encoder.encodeToString(newPassword.getBytes()));
                    userRepo.save(existUser.get());
                    return "password change successffully";
                }
            } else {
                return "user not valid";
            }
        } else {
            return "user name, old pass, new pass cant be empty";
        }
        return "something went wrong";
    }

    @Override
    public String otpVerficationSignup(String username, String otp) {
        Optional<User> existUser = userRepo.findByUsername(username);
        if (existUser.isPresent()) {
            if (existUser.get().getCreationOtp().isBefore(LocalDateTime.now()) && existUser.get().getCreationOtp().plusMinutes(5).isAfter(LocalDateTime.now())) {
                if (Objects.equals(existUser.get().getOtp(), otp)) {
                    existUser.get().setVerification(true);
                    userRepo.save(existUser.get());
                    return "Verification successfully";
                } else {
                    return "otp is invalid";
                }
            } else {
                return "your tym for otp verification is out please login again";
            }
        } else {
            return "something went wrong please try again";
        }
    }


    public void sendEmail(String toEmail,
                          String subject,
                          String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setFrom("karishma.choudhary@intelliatech.com");
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);
        javaMailSender.send(simpleMailMessage);
    }


}
