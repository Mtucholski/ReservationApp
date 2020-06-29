package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.service.MedicalClinicService;
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
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping(path = "/api/clinic")
@PreAuthorize("hasRole(@roles.OWNER)")
public class ClinicRestController {

    private final MedicalClinicService clinicService;

    @Autowired
    public ClinicRestController(MedicalClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(path = "/all-clinics", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Clinic>> getAllClinics(){

        Collection<Clinic> clinics = new ArrayList<>(clinicService.findAllClinics());

        return new ResponseEntity<>(clinics, HttpStatus.OK);
    }

    @RequestMapping(path = "/findName/{clinicName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Clinic>> findClinicInTheCity(@PathVariable("clinicName") String cityName){

        Collection<Clinic> clinics = new ArrayList<>(clinicService.findByCity(cityName));

        return new ResponseEntity<>(clinics, HttpStatus.OK);
    }

    @RequestMapping(path = "/{clinicId}", method = RequestMethod.GET)
    public ResponseEntity<Clinic> getClinicById(@PathVariable("clinicId") int clinicId){

        Clinic clinic = clinicService.findByClinicId(clinicId);
        return new ResponseEntity<>(clinic, HttpStatus.OK);
    }

    @RequestMapping(path = "/addClinic", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Clinic> addClinic(@RequestBody @Valid Clinic clinic, BindingResult result, UriComponentsBuilder builder){

        BindingErrorsResponse response = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || clinic == null){

            response.addAllErrors(result);
            headers.add("errors", response.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        clinicService.createClinic(clinic);
        headers.setLocation(builder.path("/clinic/newClinic/{clinicId}").buildAndExpand(clinic.getId()).toUri());
        return new ResponseEntity<>(clinic, headers, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/updateClinic", method = RequestMethod.PUT,consumes = "application/json",produces = "application/json")
    public ResponseEntity<Clinic> updateClinic(@RequestBody @Valid Clinic clinic, BindingResult result){

        BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || clinic == null){

            errorsResponse.addAllErrors(result);
            headers.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        clinicService.updateClinic(clinic);
        return new ResponseEntity<>(clinic, HttpStatus.OK);
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
    public ResponseEntity<Void> deleteClinic(@RequestBody @Valid Clinic clinic){

        clinicService.deleteClinic(clinic);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
