package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private boolean isActive;
    private Set<Role> roles;
}
