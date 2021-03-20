package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Specialty;
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
@PreAuthorize("hasRole(@roles.OWNER)")
@RequestMapping("api/specialties")
public class SpecialtyRestController {

    private final MedicalClinicService clinicService;

    @Autowired
    public SpecialtyRestController(MedicalClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping(path = "/find/{specialtyId}", produces = "application/json")
    public ResponseEntity<Specialty> getById(@PathVariable("specialtyId") int specialtyId) {

        Specialty specialty = clinicService.findSpecialtyById(specialtyId);

        return new ResponseEntity<>(specialty, HttpStatus.OK);
    }

    @GetMapping(path = "/all-specialties", produces = "application/json")
    public ResponseEntity<Collection<Specialty>> getAllSpecialties() {

        Collection<Specialty> specialties = new ArrayList<>(clinicService.findAllSpecialties());

        if (specialties.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    @GetMapping(path = "/specialty-name/{specialtyName})", produces = "application/json")
    public ResponseEntity<Specialty> getSpecialtyByName(@PathVariable("specialtyName") String specialtyName) {

        Specialty specialty = clinicService.findSpecialtyByName(specialtyName);

        return new ResponseEntity<>(specialty, HttpStatus.OK);
    }

    @PostMapping(path = "/create-specialty", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Specialty> createSpecialty(@RequestBody @Valid Specialty specialty, BindingResult result, UriComponentsBuilder builder){

        BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || specialty == null){

            errorsResponse.addAllErrors(result);
            headers.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        clinicService.saveSpecialty(specialty);
        headers.setLocation(builder.path("api/specialties/{id}").buildAndExpand(specialty.getSpecialtyName()).toUri());
        return new ResponseEntity<>(specialty, headers, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete", consumes ="application/json")
    public ResponseEntity<Void> deleteSpecialty(@RequestBody @Valid Specialty specialty){

        clinicService.deleteSpecialty(specialty);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

