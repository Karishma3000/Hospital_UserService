package com.hospital.managment.user.service.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "role")
@Entity
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "role")
    private String rolename;
}
