package uz.pdp.shippingservice.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.user.DriverEntity;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverInfoResponseDto {

    private GetMe me;

    private Long id;

    private float carLength;

    private float carWidth;

    private float maxLoad;

    private boolean hasFreezer;

    private boolean hasWrappedFully;

    private List<String> driverPassportPhotos;

    private List<String> driverLicensePhotos;

    private List<String> carTexPassport;

    private List<String> carPhotos;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    public static DriverInfoResponseDto toDto(DriverEntity entity){
        return DriverInfoResponseDto.builder()
                .createdAt(entity.getCreatedAt())
                .id(entity.getId())
                .carLength(entity.getCarLength())
                .carWidth(entity.getCarWidth())
                .maxLoad(entity.getMaxLoad())
                .hasFreezer(entity.isHasFreezer())
                .hasWrappedFully(entity.isHasWrappedFully())
                .build();
    }

}
