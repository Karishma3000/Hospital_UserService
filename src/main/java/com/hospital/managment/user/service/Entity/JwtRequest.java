package com.hospital.managment.user.service.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class JwtRequest {
        private String email;
        private String password;
    }

