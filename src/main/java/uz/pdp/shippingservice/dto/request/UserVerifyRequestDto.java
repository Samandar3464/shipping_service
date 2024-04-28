package uz.pdp.shippingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyRequestDto {

    private String phone;

    private String verificationCode;
}
