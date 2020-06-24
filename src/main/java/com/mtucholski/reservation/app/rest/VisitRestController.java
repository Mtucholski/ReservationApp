package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.model.Visit;
import com.mtucholski.reservation.app.service.MedicalClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class VisitRestController {


    private final MedicalClinicService clinicService;

    @Autowired()
    public VisitRestController( MedicalClinicService clinicService){

        this.clinicService = clinicService;
    }

    @PreAuthorize("hasRole(@roles.OWNER)")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Visit>> getAllVisits(){

        List<Visit> visits = new ArrayList<>(this.clinicService.findAllVisits());

        if (visits.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(visits, HttpStatus.OK);
    }
}
