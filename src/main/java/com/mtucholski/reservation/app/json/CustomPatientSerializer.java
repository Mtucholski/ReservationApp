package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.model.Visit;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;


public class CustomPatientSerializer extends StdSerializer<Patient> {


    public CustomPatientSerializer(Class<Patient> patientClass){

        super(patientClass);
    }
    @Override
    public void serialize(Patient patient, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        Format formatter = new SimpleDateFormat("dd/MM/yyyy");

        jsonGenerator.writeStartObject();
        if (patient.getId() == null){

            jsonGenerator.writeNullField("patient_id");
        }else {

            jsonGenerator.writeNumberField("patient_id", patient.getId());
        }

        jsonGenerator.writeStringField("first_name", patient.getFirstName());
        jsonGenerator.writeStringField("last_name", patient.getLastName());
        jsonGenerator.writeStringField("personal_id", patient.getPersonalID());
        jsonGenerator.writeObject(patient.getPatientAddress());
        jsonGenerator.writeStringField("telephone", patient.getTelephone());
        jsonGenerator.writeStringField("email", patient.getEmail());

        //write visits
        jsonGenerator.writeArrayFieldStart("visits");

        for (Visit visit: patient.getVisits()){

            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("visit_id", visit.getId());
            jsonGenerator.writeStringField("date", formatter.format(visit.getVisitDate()));
            jsonGenerator.writeStringField("visit_description", visit.getVisitDescription());
            jsonGenerator.writeStringField("patient_first_name", visit.getPatient().getFirstName());
            jsonGenerator.writeStringField("patient_last_name", visit.getPatient().getLastName());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
