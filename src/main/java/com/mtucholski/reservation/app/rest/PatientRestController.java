package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.service.MedicalClinicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/patients")
@PreAuthorize("hasRole(@roles.PATIENT) or hasRole(@roles.DOCTOR)")
@Slf4j
public class PatientRestController {

    private final MedicalClinicService clinicService;

    @Autowired
    public PatientRestController(MedicalClinicService clinicService) {

        this.clinicService = clinicService;
    }

    @RequestMapping(path = "/getAll", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Patient>> getAllPatients(){

        List<Patient> patients = new ArrayList<>(this.clinicService.findAllPatients());

        log.info("found patients number:" + " " + patients.size());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = "/findPatient/{patientId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Patient> getPatientById(@PathVariable("patientId") int patientId){

        Patient patient = clinicService.findPatientById(patientId);

        log.info("patient found with id:" + "" + patientId);
        return new ResponseEntity<>(patient, HttpStatus.FOUND);
    }

    @RequestMapping(path = "/patientLastName/{lastName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Patient>> getPatientsByLastName(@PathVariable("lastName") String lastName){

        List<Patient> patients = clinicService.findByLastName(lastName);

        log.info("patients found by last name:" + " " + patients.size());
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @RequestMapping(path = "/addPatient", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Patient> addNewPatient(@RequestBody @Valid Patient patient, BindingResult result,
                                                 UriComponentsBuilder builder){

        BindingErrorsResponse response = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if ( result.hasErrors() || patient == null){

            response.addAllErrors(result);
            headers.add("errors", response.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }


        clinicService.savePatient(patient);
        headers.setLocation(builder.path("/api/patients/patientId/{patientId}").buildAndExpand(patient.getId()).toUri());
        log.info("patient added" + "" + patient.toString());
        return new ResponseEntity<>(patient, headers, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/patientUpdate", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Patient> updatePatient(@RequestBody @Valid Patient patient, BindingResult result){

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || patient == null){

            errors.addAllErrors(result);
            headers.add("errors", errors.toJSON());

            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        clinicService.updatePatient(patient);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @RequestMapping(path = "/deletePatient/{patientId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePatient(@PathVariable("patientId") int patientId){

        Patient patient = clinicService.findPatientById(patientId);

        if (patient == null){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clinicService.deletePatient(patient);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
