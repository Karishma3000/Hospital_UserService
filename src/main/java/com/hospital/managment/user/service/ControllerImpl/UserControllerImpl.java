package com.hospital.managment.user.service.ControllerImpl;


import com.hospital.managment.user.service.Controller.UserController;
import com.hospital.managment.user.service.Entity.JwtRequest;
import com.hospital.managment.user.service.Entity.JwtResponse;
import com.hospital.managment.user.service.Entity.User;
import com.hospital.managment.user.service.Repository.UserRepo;
import com.hospital.managment.user.service.Secutity.JwtHelper;
import com.hospital.managment.user.service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/userAuth")
public class UserControllerImpl implements UserController {
    @Autowired
    AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public ResponseEntity<String> signUp(@RequestBody User user) {

        if(!(user.getPassword()==null && user.getUsername()==null)){
            if(!(Objects.equals(user.getPassword(), "") && Objects.equals(user.getUsername(), ""))){

                  String serviceReturn = userServiceImpl.signUp(user);
                  return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

            }else {
                return new ResponseEntity<>("username and password invalid",HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>("username and pass invalid",HttpStatus.BAD_REQUEST);
            }
    }
    @Override
    public ResponseEntity<String> otpVerficationSingup (String username, String otp){
        if (!otp.isEmpty() && !username.isEmpty()) {

            String serviceReturn = userServiceImpl.otpVerficationSignup(username, otp);
            return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

        } else {
            return new ResponseEntity<>("please enter 6 digit of otp no sended on your email", HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<JwtResponse> loginJwt(JwtRequest request) {
        Optional<User> existUser = userRepo.findByUsername(request.getEmail());

        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails  userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        String otp=userServiceImpl.generateOTP();
        LocalDateTime currentTime=LocalDateTime.now();
        existUser.get().setCreationOtp(currentTime);
        existUser.get().setOtp(otp);
        userRepo.save(existUser.get());

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername())
                .build();
        return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);


    }
    private void doAuthenticate (String username, String password){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
           try {
               manager.authenticate(authentication);
           } catch (BadCredentialsException e) {
               throw new RuntimeException("invalid Username Or Password  !!");
           }

       }
       @ExceptionHandler(BadCredentialsException.class)
       public String exceptionHandler () {
           return "Credentials Invalid !!";
       }

       @Override
       public ResponseEntity<String> otpVerficationLogin (String username, String otp){
           if (!otp.isEmpty() && !username.isEmpty()) {
               String serviceReturn = userServiceImpl.otpVerficationLogin(username, otp);
               return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

           } else {
               return new ResponseEntity<>("please enter 6 digit of otp no sended on your email", HttpStatus.BAD_REQUEST);
           }
       }

       @Override
       public ResponseEntity<String> changePassword (String username, String oldPassword, String newPassword){
           if (!username.isEmpty() && !oldPassword.isEmpty() && !newPassword.isEmpty()) {
               if (!username.isBlank() && !oldPassword.isBlank() && !newPassword.isBlank()) {

                   String serviceReturn = userServiceImpl.changePassword(username, oldPassword, newPassword);
                   return new ResponseEntity<>(serviceReturn, HttpStatus.OK);

               } else {
                   return new ResponseEntity<>("user name, old pass, new pass cant be null", HttpStatus.BAD_REQUEST);
               }
           } else {
               return new ResponseEntity<>("user name, old pass, new pass cant be black", HttpStatus.BAD_REQUEST);
           }
       }







}
