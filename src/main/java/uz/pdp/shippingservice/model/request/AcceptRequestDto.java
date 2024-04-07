package uz.pdp.shippingservice.model.request;

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
    private UUID senderId;
    private UUID announcementClientId;
}
