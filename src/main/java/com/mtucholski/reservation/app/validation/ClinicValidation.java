package com.mtucholski.reservation.app.validation;

import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.ValidationStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class ClinicValidation {


    public void validateClinic(Clinic clinic) {

        clinicNameValidation(clinic);
        checkCreationDateIsValid(clinic);

        if (clinic.getIsValid()) {

            log.info("Clinic is valid. Setting status to Valid");
            clinic.setValidationStatus(ValidationStatus.PendingValidation);

        } else {

            log.error("Clinic is invalid. Setting status to invalid");
            clinic.setValidationStatus(ValidationStatus.Invalid);
        }
    }

    public void checkIfClinicCanBeOpen(Clinic clinic){

        checkIfClinicHiredDoctors(clinic);
        checkIfClinicHasAddress(clinic);
    }

    private void clinicNameValidation(Clinic clinic) {

        log.info("checking if clinic name is empty or contains only white spaces");
        if (clinic.getClinicName().isBlank()) {

            log.error("clinic name is blank.Setting status invalid");
            clinic.setIsValid(false);
        }

        log.info("Checking if clinic name matches pattern");
        if (!clinic.getClinicName().matches("^[\\p{L} .'-]+$")) {

            log.error("clinic name contains illegal characters");
            clinic.setIsValid(false);
        }
    }

    private void checkCreationDateIsValid(Clinic clinic) {

        LocalDate date = LocalDate.ofInstant(clinic.getCreationDate().truncatedTo(ChronoUnit.DAYS), ZoneOffset.UTC);

        if (date.getYear() < LocalDate.now().minusYears(5L).getYear()) {

            log.error("Creation date is earlier than 5 years");
            clinic.setIsValid(false);
        }

        if (date.getYear() > LocalDate.now().plusYears(1L).getYear()) {

            log.error("Creation date cannot be in future");
            clinic.setIsValid(false);
        }
    }

    private void checkIfClinicHiredDoctors(Clinic clinic){

        if (clinic.getDoctors().isEmpty()){

            log.info("Clinic doesn't hire doctors");
            clinic.setValidationStatus(ValidationStatus.PendingValidation);
        } else {

            log.info("clinic has hired doctors");
            clinic.setValidationStatus(ValidationStatus.Valid);
        }
    }

    private void checkIfClinicHasAddress(Clinic clinic){

        if (clinic.getAddresses().isEmpty()){

            log.error("Clinic address is empty");
            clinic.setValidationStatus(ValidationStatus.Invalid);
        }
    }
}
