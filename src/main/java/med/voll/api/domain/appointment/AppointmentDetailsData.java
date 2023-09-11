package med.voll.api.domain.appointment;

import med.voll.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentDetailsData(Long id, Long idDoctor, Long idPatient, LocalDateTime date, Specialty specialty) {
    public AppointmentDetailsData(Appointment appointment) {
        this(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getDate(), appointment.getSpecialty());
    }
}
