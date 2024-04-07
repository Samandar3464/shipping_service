package uz.pdp.shippingservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireBaseTokenRegisterDto {

    private UUID userId;

    private String fireBaseToken;
}
