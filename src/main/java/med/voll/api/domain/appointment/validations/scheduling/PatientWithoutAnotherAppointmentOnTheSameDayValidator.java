package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientWithoutAnotherAppointmentOnTheSameDayValidator implements AppointmentSchedulingValidator {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleData data) {
        var openingAppointmentTime = data.date().withHour(7);
        var closingAppointmentTime = data.date().withHour(18);
        var patientHasAnotherAppointmentOnTheSameDay = repository.existsByPatientIdAndDateBetween(data.idPatient(), openingAppointmentTime, closingAppointmentTime);
        if(patientHasAnotherAppointmentOnTheSameDay){
            throw new ConfirmationException("Patient has another appointment on the same day");
        }

    }

}
