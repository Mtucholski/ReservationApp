package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Visit;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class VisitRestController {


    private final MedicalClinicService clinicService;
    private final BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired()
    public VisitRestController(MedicalClinicService clinicService) {

        this.clinicService = clinicService;
    }

    @PreAuthorize("hasRole(@roles.OWNER)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Visit>> getAllVisits() {

        List<Visit> visits = new ArrayList<>(this.clinicService.findAllVisits());

        if (visits.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Visit> getVisit(@PathVariable(value = "visitId") int visitId) {

        Visit visit = this.clinicService.findVisitsById(visitId);

        return new ResponseEntity<>(visit, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Visit> addVisit(@RequestBody @Valid Visit visit, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {

        if (bindingResult.hasErrors()) {

            errorsResponse.addAllErrors(bindingResult);
            httpHeaders.add("errors", errorsResponse.toJSON());

            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        this.clinicService.createVisit(visit);
        httpHeaders.setLocation(ucBuilder.path("/api/visits/{id}").buildAndExpand(visit.getId()).toUri());

        return new ResponseEntity<>(visit, httpHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(@roles.OWNER) or hasRole(@roles.DOCTOR)")
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Visit> updateVisit(@RequestBody @Valid Visit visit, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){

            errorsResponse.addAllErrors(bindingResult);
            httpHeaders.add("errors", errorsResponse.toJSON());

            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        }

        this.clinicService.updateVisit(visit);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("@hasRole(@roles.OWNER)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Visit>> getVisitWithDate(@PathVariable("date")LocalDate date){

        List<Visit> visits = new ArrayList<>(this.clinicService.findByDate(date));

        return new ResponseEntity<>(visits, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(@roles.OWNER)")
    @RequestMapping(value = "", params = "visitId", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") int visitId){

        Visit visit = clinicService.findVisitsById(visitId);
        this.clinicService.delete(visit);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
