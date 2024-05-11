package uz.pdp.shippingservice.entity.locations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.location.CityRequestDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Region region;

    public static City toEntity(CityRequestDto cityRequestDto, Region region) {
        return City.builder()
                .name(cityRequestDto.getName())
                .region(region)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public City(String name, Region region) {
        this.name = name;
        this.region = region;
    }
}
