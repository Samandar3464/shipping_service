package uz.pdp.shippingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangePasswordDto {

    @NotBlank
    @Size(min = 13, max = 13)
    private String phone;

    @NotBlank
    @Size(min = 6)
    private String newPassword;

    @NotBlank
    @Size(min = 6)
    private String oldPassword;
}
