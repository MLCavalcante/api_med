package med.voll.api.domain.appointment.validations.canceling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentCancelingData;
import med.voll.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("AdvanceTimeValidatorCanceling")
public class AdvanceTimeValidator implements CancelingAppointmentValidator {
    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancelingData data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInMinutes < 24) {
            throw new ConfirmationException("Appointment can only be canceled with a minimum notice of 24 hours");
        }
    }
}
