package med.voll.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancelingData(
        @NotNull
        Long idAppointment,

        @NotNull
        CancellationReason reason) {

}
