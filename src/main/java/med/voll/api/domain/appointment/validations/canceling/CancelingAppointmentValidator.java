package med.voll.api.domain.appointment.validations.canceling;

import med.voll.api.domain.appointment.AppointmentCancelingData;

public interface CancelingAppointmentValidator {

    void validate(AppointmentCancelingData data);
}
