package uz.pdp.shippingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.Car;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDriverResponse {
    private UUID id;

    private String currentRegion;

    private String currentCity;

    private double currentLatitude;

    private double currentLongitude;

    private UserResponseDto userResponseDto;

    private String info;

    private List<String> carPhotoPath;
    private String carColor;
    private String carNumber;
    private String carModel;
    private float maximumLoad;
    private float maximumLength;
    private float maximumLoadWidth;

    public static AnnouncementDriverResponse from(AnnouncementDriver announcementDriver) {
        Car car = announcementDriver.getCar();
        return AnnouncementDriverResponse
                .builder()
                .id(announcementDriver.getId())
                .currentRegion(announcementDriver.getRegion().getName())
                .currentCity(announcementDriver.getCity() == null ? null : announcementDriver.getCity().getName())
                .info(announcementDriver.getInfo())
                .currentLongitude(announcementDriver.getCurrentLongitude())
                .currentLatitude(announcementDriver.getCurrentLatitude())
                .carColor(car.getColor())
                .carNumber(car.getCarNumber())
                .carModel(car.getModel())
                .maximumLoad(car.getMaximumLoad())
                .maximumLength(car.getMaximumLength())
                .maximumLoadWidth(car.getMaximumLoadWidth())
                .build();
    }
}
