package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicBusinessHoursValidator implements AppointmentSchedulingValidator {

    public void validate(AppointmentScheduleData data) {
        var appointmentDate = data.date();
        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeClinicOpening =  appointmentDate.getHour() < 7;
        var afterClinicClosing = appointmentDate.getHour() > 18;
        if (sunday || beforeClinicOpening || afterClinicClosing){
            throw new ConfirmationException("Appointment outside of the clinic's operating hours");
        }

    }
}
