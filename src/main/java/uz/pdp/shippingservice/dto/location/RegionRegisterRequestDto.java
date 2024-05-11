package uz.pdp.shippingservice.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionRegisterRequestDto {
    private Integer id;
    private Integer countryId;
    private String name;
    private Boolean active;

}
