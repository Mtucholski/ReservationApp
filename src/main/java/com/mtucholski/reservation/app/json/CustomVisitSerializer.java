package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.model.Visit;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class CustomVisitSerializer extends StdSerializer<Visit> {

    public CustomVisitSerializer (Class<Visit>visitClass){

        super(visitClass);
    }

    @Override
    public void serialize(Visit visit, JsonGenerator gen, SerializerProvider provider) throws IOException {

        if (visit == null || visit.getPatient()== null){

            throw new IOException("cannot serialize visit object = visit or visit.patient is null");

        }

        Format format = new SimpleDateFormat("dd/MM/yyyy");
        gen.writeStartObject();
        checkIfIDIsNull(visit.getId(), gen);

        gen.writeStringField("visit_date", format.format(visit.getVisitDate()));
        gen.writeStringField("visit_description", visit.getVisitDescription());
        Patient patient = visit.getPatient();

        gen.writeObjectFieldStart("patient");
        gen.writeStringField("patient_personal_id", patient.getPersonalID());
        gen.writeEndObject();
    }

    private void checkIfIDIsNull(Integer id, JsonGenerator generator) throws IOException {

        if (id == null){

            generator.writeNullField("id");

        }else {

            generator.writeNumberField("visit_id", id);
        }
    }
}
