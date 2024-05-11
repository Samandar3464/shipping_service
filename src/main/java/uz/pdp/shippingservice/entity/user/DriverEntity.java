package uz.pdp.shippingservice.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.pdp.shippingservice.dto.user.DriverUpdateDto;
import uz.pdp.shippingservice.dto.user.UserRegisterDto;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "driver_info")
public class DriverEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_length")
    private float carLength;

    @Column(name = "car_width")
    private float carWidth;

    @Column(name = "max_load")
    private float maxLoad;

    @Column(name = "has_freezer")
    private boolean hasFreezer;

    @Column(name = "has_wrapped_fully")
    private boolean hasWrappedFully;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "driver_passport_photos")
    private List<Attachment> driverPassportPhotos;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "driver_license_photos")
    private List<Attachment> driverLicensePhotos;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "car_tex_passport")
    private List<Attachment> carTexPassport;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "car_photos")
    private List<Attachment> carPhotos;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public static DriverEntity toEntity(DriverUpdateDto dto) {
        return DriverEntity.builder()
                .carLength(dto.getCarLength())
                .carWidth(dto.getCarWidth())
                .maxLoad(dto.getMaxLoad())
                .hasFreezer(dto.isHasFreezer())
                .hasWrappedFully(dto.isHasWrappedFully())
                .isActive(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
