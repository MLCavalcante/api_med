package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;

public record DetailedMedicalData(Long id, String name, String email, String ml, String phone, Specialty specialty, Address address) {
    public DetailedMedicalData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getMl(), doctor.getPhone(), doctor.getSpecialty(), doctor.getAddress());


    }
}
