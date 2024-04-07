package uz.pdp.shippingservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVerifyRequestDto {

    private String phone;

    private int verificationCode;
}
