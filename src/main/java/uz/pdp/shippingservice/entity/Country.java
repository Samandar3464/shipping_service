package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.CountryDto;

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
