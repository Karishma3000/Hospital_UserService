package com.hospital.managment.user.service.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class JwtResponse {
    private String jwtToken;
    private  String username;
}
