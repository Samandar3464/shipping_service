package uz.pdp.shippingservice.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.enums.NotificationType;

import java.util.HashMap;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {

    private String title;

    private String body;

    private UUID receiverId;

    private UUID announcementDriverId;

    private UUID announcementPassengerId;


    private HashMap<String, String> date;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
