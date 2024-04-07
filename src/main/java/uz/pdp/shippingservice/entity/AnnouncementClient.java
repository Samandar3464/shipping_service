package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.model.request.AnnouncementClientDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AnnouncementClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Country fromCountry;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Country toCountry;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Region fromRegion;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Region toRegion;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City fromCity;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City toCity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private double fromLatitude;

    private double fromLongitude;

    private double toLongitude;

    private double toLatitude;

    private boolean active;

    private boolean deleted;

    private LocalDateTime timeToSend;

    private LocalDateTime createdTime;

    private String info;

    private double price;

    public static AnnouncementClient from(AnnouncementClientDto announcementRequestDto) {
        return AnnouncementClient.builder()
                .fromLatitude(announcementRequestDto.getFromLatitude())
                .fromLongitude(announcementRequestDto.getFromLongitude())
                .toLatitude(announcementRequestDto.getToLatitude())
                .toLongitude(announcementRequestDto.getToLongitude())
                .timeToSend(announcementRequestDto.getTimeToSend())
                .info(announcementRequestDto.getInfo())
                .timeToSend(announcementRequestDto.getTimeToSend())
                .createdTime(LocalDateTime.now())
                .price(announcementRequestDto.getPrice())
                .active(true)
                .deleted(false)
                .build();
    }
}
