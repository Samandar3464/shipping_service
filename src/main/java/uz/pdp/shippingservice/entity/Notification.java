package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.enums.Type;
import uz.pdp.shippingservice.dto.NotificationRequestDto;

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

    private Long senderId;

    private Long receiverId;

    private UUID announcementDriverId;

    private UUID announcementPassengerId;

    private boolean active;

    private boolean received;

    private boolean read;

    private boolean deleted;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime createdTime;

    private String receiverToken;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JsonIgnore
    private UserEntity userEntity;

    public static Notification from(NotificationRequestDto notificationRequestDto){
        return Notification.builder()
                .receiverId(notificationRequestDto.getReceiverId())
                .announcementDriverId(notificationRequestDto.getAnnouncementDriverId())
                .announcementPassengerId(notificationRequestDto.getAnnouncementPassengerId())
                .createdTime(LocalDateTime.now())
                .type(notificationRequestDto.getType())
                .received(false)
                .read(false)
                .active(true)
                .build();

    }
}
