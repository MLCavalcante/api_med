package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDetailsData;
import med.voll.api.domain.appointment.AppointmentScheduleData;
import med.voll.api.domain.appointment.AppointmentScheduling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentScheduling scheduling;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentScheduleData data) {
        var dto = scheduling.schedule(data);
        return ResponseEntity.ok(dto);
    }


}
