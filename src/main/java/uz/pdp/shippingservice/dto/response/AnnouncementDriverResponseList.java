package uz.pdp.shippingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementDriver;
import uz.pdp.shippingservice.entity.Car;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDriverResponseList {
    private UUID id;

    private String fulName;

    private String currentRegion;

    private String currentCity;

    private float maximumLoad;

    private float maximumLength;

    private float maximumLoadWidth;

    public static AnnouncementDriverResponseList from(AnnouncementDriver announcementDriver) {
        Car car = announcementDriver.getCar();
        return AnnouncementDriverResponseList
                .builder()
                .id(announcementDriver.getId())
                .currentRegion(announcementDriver.getRegion().getName())
                .currentCity(announcementDriver.getCity() == null ? null : announcementDriver.getCity().getName())
                .maximumLoad(car.getMaximumLoad())
                .maximumLength(car.getMaximumLength())
                .maximumLoadWidth(car.getMaximumLoadWidth())
                .build();
    }
    public static AnnouncementDriverResponseList fromForNotification(AnnouncementDriver announcementDriver, String fullName) {
        Car car = announcementDriver.getCar();
        return AnnouncementDriverResponseList
                .builder()
                .id(announcementDriver.getId())
                .fulName(fullName)
                .currentRegion(announcementDriver.getRegion().getName())
                .currentCity(announcementDriver.getCity() == null ? null : announcementDriver.getCity().getName())
                .maximumLoad(car.getMaximumLoad())
                .maximumLength(car.getMaximumLength())
                .maximumLoadWidth(car.getMaximumLoadWidth())
                .build();
    }
}
