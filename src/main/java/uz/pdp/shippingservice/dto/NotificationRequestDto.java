package uz.pdp.shippingservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.enums.TypeClients;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {

    @Enumerated(EnumType.STRING)
    private TypeClients type;

    private Long senderId;

    private Long receiverId;

    private UUID announcementId;

    private String message;

}