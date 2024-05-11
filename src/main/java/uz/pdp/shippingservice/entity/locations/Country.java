package uz.pdp.shippingservice.entity.locations;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.location.CountryDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true ,name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Country(Integer id, String name) {
    }

    public static Country toEntity(CountryDto countryDto){
        return Country.builder()
                .name(countryDto.getName())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
