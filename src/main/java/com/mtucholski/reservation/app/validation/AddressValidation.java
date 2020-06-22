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
        Address address = (Address) object;

        log.info("checking if address fields are empty");
        if (address.getCity().isEmpty() || address.getStreet().isEmpty() || address.getFlatNumber().isEmpty() ||
                address.getStreetNumber().isEmpty() ){

            errors.reject("[Address] cannot be null");
            log.error("encountered errors during address validation:" + "" + errors);

        }

        log.info("address fields aren't empty");
        log.info("checking if address fields match pattern");
        if (!(address.getCity()).matches(cityRegex) && !(address.getStreet().matches(cityRegex))){

            errors.reject("address city and street must match pattern");
            log.error("address city and street doesn't match pattern:" + "" + cityRegex);
        }
    }
}
