package uz.pdp.shippingservice.dto.user;

import lombok.*;
import uz.pdp.shippingservice.enums.TypeClients;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusDto {


    private TypeClients typeClients;
    
    private int stars;

    private String text;

    private Long givenTo;

    private Long givenBy;

}
