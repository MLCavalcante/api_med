package med.voll.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Boolean existsByDoctorIdAndDate(Long idDoctor, LocalDateTime date);

    Boolean existsByPatientIdAndDateBetween(Long idPatient, LocalDateTime openingAppointmentTime, LocalDateTime closingAppointmentTime);
}
