package com.mtucholski.reservation.app.repositories;

import com.mtucholski.reservation.app.model.PatientAddress;

public interface PatientAddressRepository {

    void save(PatientAddress patientAddress);
    void delete(PatientAddress patientAddress);
    void update(PatientAddress patientAddress);
}
