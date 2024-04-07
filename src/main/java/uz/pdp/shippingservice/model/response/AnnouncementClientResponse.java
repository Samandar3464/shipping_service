package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementClient;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementClientResponse {
    private UUID id;
    private String fromRegion;
    private String toRegion;
    private String fromCity;
    private String toCity;

    private double fromLatitude;
    private double fromLongitude;
    private double toLatitude;
    private double toLongitude;

    private String info;
    private double price;
    private String timeToSend;
    private UserResponseDto userResponseDto;

    public static AnnouncementClientResponse from(AnnouncementClient announcementClient, UserResponseDto userResponseDto) {
        return AnnouncementClientResponse.builder()
                .id(announcementClient.getId())
                .fromRegion(announcementClient.getFromRegion().getName())
                .toRegion(announcementClient.getToRegion().getName())
                .fromCity(announcementClient.getFromCity().getName())
                .toCity(announcementClient.getToCity().getName())
                .price(announcementClient.getPrice())
                .fromLatitude(announcementClient.getFromLatitude())
                .fromLongitude(announcementClient.getFromLongitude())
                .toLatitude(announcementClient.getToLatitude())
                .toLongitude(announcementClient.getToLongitude())
                .info(announcementClient.getInfo())
                .timeToSend(announcementClient.getTimeToSend().toString())
                .userResponseDto(userResponseDto)
                .build();
    }
}
