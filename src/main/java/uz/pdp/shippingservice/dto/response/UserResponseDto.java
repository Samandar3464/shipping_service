package uz.pdp.shippingservice.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;

    private String fullName;

    private String phone;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate birthDate;

    private double status;

    private Gender gender;

    private String profilePhotoUrl;


    public static UserResponseDto from(UserEntity userEntity) {
//        double status= (double) (userEntity.getStatus().getStars()) / userEntity.getStatus().getCount();
        return UserResponseDto.builder()
                .id(userEntity.getId())
//                .fullName(userEntity.getFullName())
                .phone(userEntity.getUsername())
//                .birthDate(userEntity.getBirthDate()==null ? null : userEntity.getBirthDate())
//                .gender(userEntity.getGender())
//                .status(status)
                .build();
    }
}
