package com.example.Customer.Website.models.SecurityModels;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String role;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return role;
    }

    public enum Roles {
        ROLE_USER,
        ROLE_ADMIN
    }
}