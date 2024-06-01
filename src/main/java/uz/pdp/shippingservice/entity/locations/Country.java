package uz.pdp.shippingservice.entity.locations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    @Column(name = "active")
    private Boolean active;

    @Column(name = "created_at")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public Country(Integer id, String name) {
    }

    public static Country toEntity(CountryDto countryDto){
        return Country.builder()
                .name(countryDto.getName())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
