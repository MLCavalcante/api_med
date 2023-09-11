package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
 public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String ml;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    private Boolean active;

   public Doctor(MedicalDataRegistry data) {
      this.active = true;
      this.name = data.name();
      this.email = data.email();
      this.phone = data.phone();
      this.ml = data.ml();
      this.specialty = data.specialty();
      this.address = new Address(data.address());
   }

   public void updateInfo(UpdateMedicalDataRegistry data) {
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

    public void inactivate() {
       this.active = false;
    }
}