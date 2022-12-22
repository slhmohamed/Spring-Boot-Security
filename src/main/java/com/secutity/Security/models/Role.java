package com.secutity.Security.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Role  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;

    }

}
