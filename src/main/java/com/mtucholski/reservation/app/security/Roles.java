package com.mtucholski.reservation.app.security;

import org.springframework.stereotype.Component;

@Component
public class Roles {

    public final String PATIENT = "ROLE_PATIENT";
    public final String DOCTOR = "ROLE_DOCTOR";
    public final String OWNER = "ROLE_OWNER";
}
