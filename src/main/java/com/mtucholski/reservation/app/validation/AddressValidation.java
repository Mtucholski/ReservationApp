package com.mtucholski.reservation.app.validation;

import com.mtucholski.reservation.app.model.PatientAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class AddressValidation implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return PatientAddress.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        String cityRegex = "^[\\p{L} .'-]+$";
        PatientAddress patientAddress = (PatientAddress) object;

        log.info("checking if patientAddress fields are empty");
        if (patientAddress.getCity().isEmpty() || patientAddress.getStreet().isEmpty() || patientAddress.getFlatNumber().isEmpty()){

            errors.reject("[PatientAddress] cannot be null");
            log.error("encountered errors during patientAddress validation:" + "" + errors);

        }

        log.info("patientAddress fields aren't empty");
        log.info("checking if patientAddress fields match pattern");
        if (!(patientAddress.getCity()).matches(cityRegex) && !(patientAddress.getStreet().matches(cityRegex))){

            errors.reject("patientAddress city and street must match pattern");
            log.error("patientAddress city and street doesn't match pattern:" + "" + cityRegex);
        }
    }
}
