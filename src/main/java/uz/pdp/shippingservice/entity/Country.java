package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.model.CountryDto;
import uz.pdp.shippingservice.model.request.RegionRegisterRequestDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    public static Country from(CountryDto countryDto){
        return Country.builder().name(countryDto.getName()).build();
    }
}
