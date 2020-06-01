package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mtucholski.reservation.app.model.Address;
import com.mtucholski.reservation.app.model.Clinic;
import com.mtucholski.reservation.app.model.Patient;

import java.io.IOException;
import java.util.Collections;

public class CustomAddressDeserializer extends StdDeserializer<Address> {


    public CustomAddressDeserializer(Class<Address> addressClass) {

        super(addressClass);
    }

    @Override
    public Address deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        Address address = new Address();
        Patient patient;
        Clinic clinic;
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode patientNode = node.get("patient");
        JsonNode clinicNode = node.get("clinic");

        patient = objectMapper.treeToValue(patientNode, Patient.class);
        clinic = objectMapper.treeToValue(clinicNode, Clinic.class);

        int addressID = node.get("address_id").asInt();
        String street = node.get("street").asText();
        String streetNumber = node.get("street_number").asText();
        String flatNumber = node.get("flat_number").asText();
        String city = node.get("city").asText();

        if (!(addressID == 0)) {

            address.setId(addressID);
        }
        address.setCity(city);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setFlatNumber(flatNumber);
        address.setPatient(patient);
        address.setClinics(Collections.singletonList(clinic));

        return address;
    }
}
