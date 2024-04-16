package uz.pdp.shippingservice.dto.response;

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
public class AnnouncementClientResponseList {
    private UUID id;
    private String toRegion;
    private String fromRegion;
    private String fromCity;
    private String toCity;
    private String timeToSend;
    public static AnnouncementClientResponseList from(AnnouncementClient announcementClient) {
        return AnnouncementClientResponseList.builder()
                .id(announcementClient.getId())
                .fromRegion(announcementClient.getFromRegion().getName())
                .toRegion(announcementClient.getToRegion().getName())
                .fromCity(announcementClient.getFromCity().getName())
                .toCity(announcementClient.getToCity().getName())
                .timeToSend(announcementClient.getTimeToSend().toString())
                .build();
    }
}
