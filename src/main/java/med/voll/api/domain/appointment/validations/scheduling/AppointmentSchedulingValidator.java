package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.appointment.AppointmentScheduleData;

public interface AppointmentSchedulingValidator {

    void validate(AppointmentScheduleData data);

}
