package med.voll.api.domain.appointment;


import med.voll.api.domain.ConfirmationException;
import med.voll.api.domain.appointment.validations.canceling.CancelingAppointmentValidator;
import med.voll.api.domain.appointment.validations.scheduling.AppointmentSchedulingValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentScheduling {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentSchedulingValidator> validators;

    @Autowired
    private List<CancelingAppointmentValidator> cancellationValidators;


    public AppointmentDetailsData schedule(AppointmentScheduleData data){

        if(!patientRepository.existsById(data.idPatient())){
            throw new ConfirmationException("Patient Id does not exist");
        }

        if(data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor())){
            throw new ConfirmationException("Doctor Id does not exist");
        }

        validators.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);
        if(doctor == null){
            throw new ConfirmationException("There is no doctor available on the date");
        }
        var appointment = new Appointment(null, doctor, patient, data.date(), data.specialty(), null);
        appointmentRepository.save(appointment);

        return new AppointmentDetailsData(appointment);

    }

    public void canceling(AppointmentCancelingData data) {
        if (!appointmentRepository.existsById(data.idAppointment())) {
            throw new ConfirmationException("Appointment Id does not exist");
        }

        cancellationValidators.forEach(v -> v.validate(data));

        var appointment = appointmentRepository.getReferenceById(data.idAppointment());
        appointment.canceling(data.reason());
    }

    private Doctor chooseDoctor(AppointmentScheduleData data) {
        if(data.idDoctor() != null){
            return doctorRepository.getReferenceById(data.idDoctor());
        }

        if(data.specialty() != null){
            throw new ConfirmationException("Specialty is mandatory when a doctor is not chosen!");
        }

        return doctorRepository.chooseAvailableDoctorOnDate(data.specialty(), data.date());
    }
}
