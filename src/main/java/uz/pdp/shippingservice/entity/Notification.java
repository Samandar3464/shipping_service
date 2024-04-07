package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.enums.NotificationType;
import uz.pdp.shippingservice.model.request.NotificationRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID senderId;

    private UUID receiverId;

    private UUID announcementDriverId;

    private UUID announcementPassengerId;

    private boolean active;

    private boolean received;

    private boolean read;

    private boolean deleted;

    private LocalDateTime createdTime;

    private String receiverToken;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JsonIgnore
    private User user;

    public static Notification from(NotificationRequestDto notificationRequestDto){
        return Notification.builder()
                .receiverId(notificationRequestDto.getReceiverId())
                .announcementDriverId(notificationRequestDto.getAnnouncementDriverId())
                .announcementPassengerId(notificationRequestDto.getAnnouncementPassengerId())
                .createdTime(LocalDateTime.now())
                .notificationType(notificationRequestDto.getNotificationType())
                .received(false)
                .read(false)
                .active(true)
                .build();

    }
}
