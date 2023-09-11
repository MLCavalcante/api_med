package med.voll.api.domain.doctor;

import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientDataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("It should return null when the only registered doctor is not available on the date")
    void chooseAvailableDoctorOnDateCase1() {
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Doctor", "doctor@vollmed.com", "123456", Specialty.DERMATOLOGY);
        var patient = registerPatient("Patient", "patient@email.com", "1234567890");
        appointmentSchedule(doctor, patient, nextMondayAt10, Specialty.DERMATOLOGY);

        var availableDoctor = doctorRepository.chooseAvailableDoctorOnDate(Specialty.GYNECOLOGY, nextMondayAt10);
        assertThat(availableDoctor).isNull();

    }

    @Test
    @DisplayName("should return doctor when available on the date")
    void chooseAvailableDoctorOnDateCase2() {
        var nextMondayAt10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Doctor", "doctor@vollmed.com", "123456", Specialty.DERMATOLOGY);

        var availableDoctor = doctorRepository.chooseAvailableDoctorOnDate(Specialty.DERMATOLOGY, nextMondayAt10);
        assertThat(availableDoctor).isEqualTo(doctor);

    }

    private void appointmentSchedule(Doctor doctor, Patient patient, LocalDateTime date, Specialty specialty) {
        em.persist(new Appointment(null, doctor, patient, date,specialty, null));
    }

    private Doctor registerDoctor(String name, String email, String ml, Specialty specialty) {
        var doctor = new Doctor (doctorData(name, email, ml, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String ssn) {
        var patient = new Patient(patientData(name, email, ssn));
        em.persist(patient);
        return patient;
    }

    private MedicalDataRegistry doctorData(String name, String email, String ml, Specialty specialty) {
        return new MedicalDataRegistry(
                name,
                email,
                "61999999999",
                ml,
                specialty,
                addressData()
        );
    }

    private PatientDataRegistry patientData(String name, String email, String ssn) {
        return new PatientDataRegistry(
                name,
                email,
                "61999999999",
                ssn,
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "street xpto",
                "bairrobairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}