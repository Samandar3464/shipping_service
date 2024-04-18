package uz.pdp.shippingservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptRequestDto {
    private Integer senderId;
    private UUID announcementClientId;
}
