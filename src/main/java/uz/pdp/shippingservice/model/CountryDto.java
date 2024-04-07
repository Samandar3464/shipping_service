package uz.pdp.shippingservice.model;

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

    public CountryDto(String name) {
        this.name = name;
    }
}
