package med.voll.api.domain.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;


@Table(name = "patients")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String ssn;

    @Embedded
    private Address address;

    private Boolean active;

    public Patient(PatientDataRegistry data) {
        this.active = true;
        this.name = data.name();
        this.email = data.email();
        this.phone = data.phone();
        this.ssn = data.ssn();
        this.address = new Address(data.address());
    }

    public void updateInfo(UpdatePatientDataRegistry data) {
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.phone() != null) {
            this.phone = data.phone();
        }
        if (data.address() != null) {
            this.address.updateInfo(data.address());
        }
    }

    public void inactivate() { this.active = false;}
}
