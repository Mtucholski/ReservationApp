package com.mtucholski.reservation.app.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mtucholski.reservation.app.model.PatientAddress;
import com.mtucholski.reservation.app.model.Patient;
import com.mtucholski.reservation.app.model.Visit;

import java.io.IOException;
import java.util.Collections;

public class CustomPatientDeserializer extends StdDeserializer<Patient> {


    public CustomPatientDeserializer(Class<Patient> patientClass){

        super(patientClass);
    }

    @Override
    public Patient deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        Patient patient = new Patient();
        Visit visit;
        PatientAddress patientAddress;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode visitNode = jsonNode.get("visit");
        JsonNode addresses = jsonNode.get("patientAddress");
        patientAddress = mapper.treeToValue(addresses, PatientAddress.class);
        visit = mapper.treeToValue(visitNode, Visit.class);

        int patientId = jsonNode.get("patient_id").asInt();
        String firstName = jsonNode.get("first_name").asText(null);
        String lastName = jsonNode.get("last_name").asText(null);
        String personalId = jsonNode.get("personal_id").asText();
        String telephone = jsonNode.get("telephone").asText(null);
        String email = jsonNode.get("email").asText(null);

        if (!(patientId == 0)){

            patient.setId(patientId);
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPersonalID(personalId);
        patient.setTelephone(telephone);
        patient.setEmail(email);
        patient.setPatientAddress(patientAddress);
        patient.setVisits(Collections.singleton(visit));

        return patient;
    }
}
