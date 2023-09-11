package med.voll.api.domain.appointment.validations.scheduling;

import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctorValidator implements AppointmentSchedulingValidator {

    @Autowired
    private DoctorRepository repository;
    public void validate(AppointmentScheduleData data){
        if(data.idDoctor() == null){
            return;
        }

        var doctorIsActive = repository.findActiveById(data.idDoctor());
        if (!doctorIsActive){
           throw new ConfirmationException("Appointment cannot be scheduled with an inactive doctor");
        }


    }

}
