package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private String neighborhood;
    private String zip_code;
    private String number;
    private String complement;
    private String city;
    private String state;

    public Address(AddressData data) {
        this.street = data.street();
        this.neighborhood = data.neighborhood();
        this.zip_code = data.zip_code();
        this.number = data.number();
        this.complement = data.complement();
        this.city = data.city();
        this.state = data.state();

    }

    public void updateInfo(AddressData data) {

        if (data.street() != null) {
            this.street = data.street();
        }
        if (data.neighborhood() != null) {
            this.neighborhood = data.neighborhood();
        }
        if (data.zip_code() != null) {
            this.zip_code = data.zip_code();
        }
        if (data.state() != null) {
            this.state = data.state();
        }
        if (data.city() != null) {
            this.city = data.city();
        }
        if (data.number() != null) {
            this.number = data.number();
        }
        if (data.complement() != null) {
            this.complement = data.complement();
        }

    }
}
