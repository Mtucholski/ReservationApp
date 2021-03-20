package com.mtucholski.reservation.app.validation;

import com.mtucholski.reservation.app.model.Clinic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ClinicValidation implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Clinic.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        Clinic clinic = (Clinic) object;

        log.info("checking if fields are empty");
        if (clinic.getClinicName().isEmpty() || clinic.getDoctors().isEmpty()){

            log.error("required fields cannot be empty");
            errors.reject("clinic fields are empty");
        }

        log.info("checking if clinic name match pattern");
        if (!(clinic.getClinicName().matches("^[\\p{L} .'-]+$"))){

            log.error("clinic name must match pattern");
            errors.reject("clinic name must match pattern");
        }
    }
}
