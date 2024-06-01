package uz.pdp.shippingservice.entity.locations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
