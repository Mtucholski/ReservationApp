package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.MedicalDoctor;

import java.io.IOException;

public class CustomClinicSerializer extends StdSerializer<Clinic> {

    public CustomClinicSerializer(Class<Clinic> clinicClass) {

        super(clinicClass);
    }

    @Override
    public void serialize(Clinic clinic, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        if ((clinic == null) || (clinic.getId() == null)) {

            throw new IOException("Cannot serialize clinic object - clinic object is null!!");
        }

        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("clinic_id", clinic.getId());
        jsonGenerator.writeStringField("clinic_name", clinic.getClinicName());
        jsonGenerator.writeArrayFieldStart("doctors");
        jsonGenerator.writeObjectField("clinic_address", clinic.getClinicAddress());

        for (MedicalDoctor doctor : clinic.getDoctors()){

            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("first_name", doctor.getFirstName());
            jsonGenerator.writeStringField("last_name", doctor.getLastName());
            jsonGenerator.writeNumberField("medical_license", doctor.getMedicalLicenseNumber());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
