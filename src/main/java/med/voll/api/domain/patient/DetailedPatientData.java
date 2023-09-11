package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record DetailedPatientData(Long id, String name, String email, String phone, String ssn, Address address) {

     public DetailedPatientData(Patient patient){
         this(patient.getId(), patient.getName(), patient.getEmail(), patient.getPhone(), patient.getSsn(), patient.getAddress());
     }
}
