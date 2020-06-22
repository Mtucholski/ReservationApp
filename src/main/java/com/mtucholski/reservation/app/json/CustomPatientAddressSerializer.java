package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mtucholski.reservation.app.model.PatientAddress;

import java.io.IOException;

public class CustomPatientAddressSerializer extends StdSerializer<PatientAddress> {


    public CustomPatientAddressSerializer(Class<PatientAddress> addressClass) {

        super(addressClass);
    }

    @Override
    public void serialize(PatientAddress patientAddress, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        if ((patientAddress.getId() == null)) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("address_id", patientAddress.getId());
        }

        jsonGenerator.writeStringField("city", patientAddress.getCity());
        jsonGenerator.writeStringField("street", patientAddress.getStreet());
        jsonGenerator.writeStringField("flat_number", patientAddress.getFlatNumber());
        jsonGenerator.writeObject(patientAddress.getPatient());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
