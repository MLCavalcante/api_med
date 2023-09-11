package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Duration;

@Component
public class AdvanceTimeValidator implements AppointmentSchedulingValidator {

    public void validate(AppointmentScheduleData data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ConfirmationException("The appointment must be scheduled with a minimum advance notice of 30 minutes");
        }

    }
}
