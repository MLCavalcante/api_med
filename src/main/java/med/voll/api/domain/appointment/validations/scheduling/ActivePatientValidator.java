package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientValidator implements AppointmentSchedulingValidator {

    @Autowired
    private PatientRepository repository;

    public void validate(AppointmentScheduleData data) {
        var patientIsActive= repository.findActiveById(data.idPatient());
        if(!patientIsActive){
            throw new ConfirmationException("Appointment cannot be scheduled with an inactive patient");
        }

    }
}
