package uz.pdp.shippingservice.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.shippingservice.enums.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

     private MultipartFile profilePhoto;

     private String fullName;

     private LocalDate brithDay;

     @Enumerated(EnumType.STRING)
     private Gender gender;
}