package uz.pdp.shippingservice.dto.user;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class DriverUpdateDto {

    private String carNumber;

    private String color;

    private String model;

    private float carLength;

    private float carWidth;

    private float maxLoad;

    private boolean hasFreezer;

    private boolean hasWrappedFully;

    private Long userId;

    private List<MultipartFile> driverPassportPhotos;

    private List<MultipartFile> driverLicensePhotos;

    private List<MultipartFile> carTexPassport;

    private List<MultipartFile> carPhotos;


}
