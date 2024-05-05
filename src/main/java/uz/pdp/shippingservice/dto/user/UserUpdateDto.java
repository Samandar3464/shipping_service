package uz.pdp.shippingservice.dto.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.shippingservice.enums.Gender;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private Long id;

    private String name;

    private String surname;

    private String fatherName;

    private String birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private MultipartFile avatar;

}
