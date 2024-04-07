package uz.pdp.shippingservice.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.Status;
import uz.pdp.shippingservice.enums.Gender;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private String fullName;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phone;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Status status;
}
