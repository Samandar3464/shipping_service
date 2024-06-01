package uz.pdp.shippingservice.dto.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.entity.user.UserRole;
import uz.pdp.shippingservice.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMe implements Serializable {

    private Long id;

    private String phone;

    private String name;

    private String surname;

    private String fatherName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    private String firebaseToken;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String avatarUrl;

    private List<UserRole> roles;

    public static GetMe toDto(UserEntity entity) {
        return GetMe.builder()
                .id(entity.getId())
                .phone(entity.getPhone())
                .name(entity.getName())
                .surname(entity.getSurname())
                .fatherName(entity.getFatherName())
                .birthDate(entity.getBirthDate())
                .createdAt(entity.getCreatedAt())
                .firebaseToken(entity.getFirebaseToken())
                .gender(entity.getGender())
                .roles(entity.getRole())
                .build();
    }

}
