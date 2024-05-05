package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double price;

    private String name;

    private String ownerData;

    private String url;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime endDate;

    private boolean active;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "content_id")
    private Attachment content;

    public static Advertising toEntity(AdvertisingRequestDto dto){
        return Advertising
                .builder()
                .price(dto.getPrice())
                .name(dto.getName())
                .ownerData(dto.getOwnerData())
                .url(dto.getUrl())
                .active(true)
                .build();
    }
}
