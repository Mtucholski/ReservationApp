package com.mtucholski.reservation.app.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Component
@Slf4j
@NoArgsConstructor
public class ExceptionsControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        ObjectMapper mapper = new ObjectMapper();
        ErrorInfo errorInfo = new ErrorInfo(e);
        String respJSONstring = "{}";
        try {
            respJSONstring = mapper.writeValueAsString(errorInfo);
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }
        return ResponseEntity.badRequest().body(respJSONstring);
    }

    @ExceptionHandler(ClinicException.class)
    public ResponseEntity<String> clinicExceptions(ClinicException e) {

        ObjectMapper mapper = new ObjectMapper();
        ErrorInfo info = new ErrorInfo(e);
        String json = "{}";

        try {

            json = mapper.writeValueAsString(info);
        } catch (JsonProcessingException exception) {

            log.error("exception in parsing json" + "" + exception.getMessage() + " " + exception.getCause());
        }

        return ResponseEntity.badRequest().body(json);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {

        log.error("access denied" + " " + ex.getCause() + "" + request);
        return new ResponseEntity<>("Access denied", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    private static class ErrorInfo {
        public final String className;
        public final String exMessage;

        public ErrorInfo(Exception ex) {
            this.className = ex.getClass().getName();
            this.exMessage = ex.getLocalizedMessage();
        }
    }
}
