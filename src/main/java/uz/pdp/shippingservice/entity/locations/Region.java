package uz.pdp.shippingservice.entity.locations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.location.RegionRegisterRequestDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Country country;

    public Region(Integer id, String name, Country country) {
    }

    public static Region toEntity(RegionRegisterRequestDto regionRegisterRequestDto, Country country){
        return Region.builder()
                .name(regionRegisterRequestDto.getName())
                .country(country)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
