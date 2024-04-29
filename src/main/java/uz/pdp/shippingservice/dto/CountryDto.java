package uz.pdp.shippingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    private Integer id;

    private String name;

    private Boolean active = true;

    public CountryDto(String name) {
        this.name = name;
    }
}
