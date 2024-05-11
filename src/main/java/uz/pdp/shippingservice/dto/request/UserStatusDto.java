package uz.pdp.shippingservice.dto.request;

import lombok.*;
import uz.pdp.shippingservice.enums.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusDto {


    private Type type;
    
    private int stars;

    private String text;

    private Long givenTo;

    private Long givenBy;

}
