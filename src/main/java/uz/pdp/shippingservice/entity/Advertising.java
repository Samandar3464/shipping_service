package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.request.AdvertisingRequestDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Advertising {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double price;

    private String name;

    private String ownerData;

    private String url;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime endDate;

    private boolean active;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment content;

    public static Advertising from(AdvertisingRequestDto advertisingRequestDto){
        return Advertising
                .builder()
                .price(advertisingRequestDto.getPrice())
                .name(advertisingRequestDto.getName())
                .ownerData(advertisingRequestDto.getOwnerData())
                .url(advertisingRequestDto.getUrl())
                .active(true)
                .build();
    }
}
