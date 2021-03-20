package com.mtucholski.reservation.app.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Component
@Slf4j
public class CustoAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        String json = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        httpServletResponse.getOutputStream().println(String.format(json, e.getMessage(), Calendar.getInstance().getTime()));
    }
}
