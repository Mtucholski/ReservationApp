package com.mtucholski.reservation.app.dto;

import com.mtucholski.reservation.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private User user;
    private String roleName;
}
