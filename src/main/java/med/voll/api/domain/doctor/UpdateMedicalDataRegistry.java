package med.voll.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.AddressData;

public record UpdateMedicalDataRegistry(
        @NotNull
        Long id,
        String name, String phone, AddressData address){

}
