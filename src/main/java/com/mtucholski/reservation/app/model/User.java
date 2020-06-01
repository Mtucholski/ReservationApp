package com.mtucholski.reservation.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "username", nullable = false)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty
    @Size(min =6 ,max = 12)
    private String password;

    @Column(name= "is_active")
    @NotEmpty
    private Boolean isActive;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Role> roles;

    @JsonIgnore
    public void addRole(String roleName){
        if (this.roles == null){

            this.roles = new HashSet<>();
        }

        Role role = new Role();
        role.setRoleName(roleName);
        this.roles.add(role);
    }
}
