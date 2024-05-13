package com.hospital.managment.user.service.Controller;


import com.hospital.managment.user.service.Entity.JwtRequest;
import com.hospital.managment.user.service.Entity.JwtResponse;
import com.hospital.managment.user.service.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserController {
    @PostMapping("/singUp")
    public ResponseEntity<String> signUp(@RequestBody User user);
    @PostMapping("/twostepverification")
    public ResponseEntity<String> otpVerficationLogin(@RequestParam String username,@RequestParam String otp);
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam String username,@RequestParam String oldPassword,@RequestParam String newPassword);
    @PostMapping("/loginJwt")
    public ResponseEntity<JwtResponse> loginJwt(@RequestBody JwtRequest request);
    @PostMapping("/signupverification")
    public ResponseEntity<String> otpVerficationSingup(@RequestParam String username,@RequestParam String otp);
}
