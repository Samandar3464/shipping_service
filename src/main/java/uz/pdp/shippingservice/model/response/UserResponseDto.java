package uz.pdp.shippingservice.model.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;

    private String fullName;

    private String phone;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDate birthDate;

    private double status;

    private Gender gender;

    private String profilePhotoUrl;


    public static UserResponseDto from(User user) {
        double status= (double) (user.getStatus().getStars()) /user.getStatus().getCount();
        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate()==null ? null : user.getBirthDate())
                .gender(user.getGender())
                .status(status)
                .build();
    }
}
