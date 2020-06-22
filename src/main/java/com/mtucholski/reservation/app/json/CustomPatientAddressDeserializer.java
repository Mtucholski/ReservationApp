package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.model.PatientAddress;

import java.io.IOException;

public class CustomPatientAddressDeserializer extends StdDeserializer<PatientAddress> {


    public CustomPatientAddressDeserializer(Class<PatientAddress> addressClass) {

        super(addressClass);
    }

    @Override
    public PatientAddress deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        PatientAddress patientAddress = new PatientAddress();
        Patient patient;
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode patientNode = node.get("patient");
        patient = objectMapper.treeToValue(patientNode, Patient.class);


        int addressID = node.get("address_id").asInt();
        String street = node.get("street").asText();
        String flatNumber = node.get("flat_number").asText();
        String city = node.get("city").asText();

        if (!(addressID == 0)) {

            patientAddress.setId(addressID);
        }
        patientAddress.setCity(city);
        patientAddress.setStreet(street);
        patientAddress.setFlatNumber(flatNumber);
        patientAddress.setPatient(patient);
        return patientAddress;
    }
}
