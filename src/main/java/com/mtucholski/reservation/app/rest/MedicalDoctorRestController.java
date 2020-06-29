package com.mtucholski.reservation.app.rest;

import com.mtucholski.reservation.app.exceptions.BindingErrorsResponse;
import com.mtucholski.reservation.app.model.Doctor;
import com.mtucholski.reservation.app.service.MedicalClinicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;


@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/doctors")
@PreAuthorize("hasRole(@roles.OWNER)")
@Slf4j
public class MedicalDoctorRestController {

    private final MedicalClinicService clinicService;
    private final BindingErrorsResponse errorsResponse;

    @Autowired
    public MedicalDoctorRestController(MedicalClinicService clinicService, BindingErrorsResponse errorsResponse) {

        this.clinicService = clinicService;
        this.errorsResponse = errorsResponse;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Doctor>> getAllDoctors() {

        List<Doctor> doctors = new ArrayList<>(clinicService.findDoctors());

        if (doctors.isEmpty()) {

            log.error("doctors not found. Something wrong please contact with admin");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("found doctors:" + " " + doctors.size());
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping(value = "/findLicense/{licenseNumber}", produces = "application/json")
    public ResponseEntity<Doctor> getDoctorNyLicenseNumber(@PathVariable("licenseNumber") int medicalLicenseNumber) {

        Doctor doctor = clinicService.findDoctorByLicenseNumber(medicalLicenseNumber);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping(value = "/{doctorId}", produces = "application/json")
    public ResponseEntity<Doctor> findDoctorById(@PathVariable("doctorId") int id) {

        Doctor doctor = clinicService.findDoctorById(id);

        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @GetMapping(value = "/findSpecialty/{specialtyName}", produces = "application/json")
    public ResponseEntity<List<Doctor>> getDoctorsWithSpecialty(@PathVariable("specialtyName") String specialtyName){

        List<Doctor> doctors = clinicService.findBySpecialtyName(specialtyName);

        if (doctors.isEmpty()){

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PostMapping(value = "/addDoctor", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Doctor> addDoctor(@RequestBody @Valid Doctor doctor, BindingResult result, UriComponentsBuilder uri){

        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || doctor == null){

            log.error("Error occurred: " + "" + result.getFieldErrors());
            errorsResponse.addAllErrors(result);
            headers.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        this.clinicService.saveDoctor(doctor);
        headers.setLocation(uri.path("/api/doctors/{doctorId}").buildAndExpand(doctor.getId()).toUri());
        return new ResponseEntity<>(doctor, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/updateDoctor", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Doctor> updateDoctor(@RequestBody @Valid Doctor newDoctor, BindingResult result){

        HttpHeaders headers = new HttpHeaders();

        if (result.hasErrors() || newDoctor == null){

            errorsResponse.addAllErrors(result);
            headers.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }

        clinicService.updateDoctor(newDoctor);
        return new ResponseEntity<>(newDoctor,HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/deleteDoctor/{id}", method = RequestMethod.DELETE,consumes = "application/json")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") int id){

        Doctor doctor = clinicService.findDoctorById(id);
        clinicService.deleteDoctor(doctor);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
