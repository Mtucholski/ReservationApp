package com.mtucholski.reservation.app.validation;

import com.mtucholski.reservation.app.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class AddressValidation implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Address.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        String cityRegex = "^[\\p{L} .'-]+$";
        Address potentialAddress = (Address) object;

        log.info("checking if patientAddress fields are empty");
        if (potentialAddress.getCity().isEmpty() || potentialAddress.getStreet().isEmpty()){

            errors.reject("[PatientAddress] cannot be null");
            log.error("encountered errors during patientAddress validation:" + "" + errors);

        }

        log.info("checking if patientAddress fields match pattern");
        if (!(potentialAddress.getCity()).matches(cityRegex) && !(potentialAddress.getStreet().matches(cityRegex))){

            errors.reject("patientAddress city and street must match pattern");
            log.error("patientAddress city  doesn't match pattern:" + "" + cityRegex);
        }
    }
}
