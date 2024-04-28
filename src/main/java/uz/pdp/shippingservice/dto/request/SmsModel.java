package uz.pdp.shippingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsModel {

    @NotBlank
    @Size(min = 13,max = 13)
    private String phone;

    private String message;

    private int from;

    public  String callback_url;

}
