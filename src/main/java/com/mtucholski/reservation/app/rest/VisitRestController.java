package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Visit;
import com.mtucholski.reservation.app.service.MedicalClinicService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class VisitRestController {

    private final MedicalClinicService clinicService;

    public VisitRestController(MedicalClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Collection<Visit>> getAllVisits() {

        Collection<Visit> visits = new ArrayList<>(this.clinicService.findAllVisits());
        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Visit> getVisit(@PathVariable("visitId") int visitId) {

        Visit visit = this.clinicService.findVisitsById(visitId);
        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Visit> addVisit(@RequestBody @Valid Visit visit, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (bindingResult.hasErrors() || (visit == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        this.clinicService.createVisit(visit);
        headers.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri());
        return new ResponseEntity<>(visit, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Visit> updateVisit(@RequestBody @Valid Visit visit, BindingResult bindingResult) {

        BindingErrorsResponse errors = new BindingErrorsResponse();
        HttpHeaders headers = new HttpHeaders();

        if (bindingResult.hasErrors() || (visit == null)) {

            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        this.clinicService.updateVisit(visit);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole(@roles.OWNER)")
    @RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE, produces = "application/json")
    @Transactional
    public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") int visitId) {

        Visit visit = this.clinicService.findVisitsById(visitId);

        this.clinicService.delete(visit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}