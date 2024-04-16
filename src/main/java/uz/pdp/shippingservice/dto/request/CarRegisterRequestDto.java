package uz.pdp.shippingservice.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRegisterRequestDto {

    private String color;

    private String carNumber;

    private String model;

    private float maximumLoad;

    private float maximumLength;

    private float maximumLoadWidth;

    private String texPassportNumber;

    private MultipartFile photoDriverLicense;

    private MultipartFile texPassportPhoto;

    private List<MultipartFile> carPhotoList;

}
