package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.validations.scheduling.AppointmentSchedulingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorWithAnotherAppointmentAtTheSameTimeValidator implements AppointmentSchedulingValidator {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleData data) {
        boolean doctorHasAnotherAppointmentAtTheSameTime = repository.existsByDoctorIdAndDate(data.idDoctor(), data.date());
        if(doctorHasAnotherAppointmentAtTheSameTime){
            throw new ConfirmationException("doctor has another appointment at the same time");
        }
    }
}
