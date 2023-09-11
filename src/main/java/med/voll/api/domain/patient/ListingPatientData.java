package med.voll.api.domain.patient;

public record ListingPatientData(Long id, String name, String email, String ssn){

    public ListingPatientData(Patient patient){
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getSsn());
    }
}
