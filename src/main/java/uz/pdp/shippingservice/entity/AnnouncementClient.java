package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.announcementClient.AnnouncementClientCreateDto;
import uz.pdp.shippingservice.entity.locations.City;
import uz.pdp.shippingservice.entity.locations.Country;
import uz.pdp.shippingservice.entity.locations.Region;
import uz.pdp.shippingservice.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "announcement_client")
public class AnnouncementClient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "from_country")
    private Country fromCountry;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "to_country")
    private Country toCountry;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "from_region")
    private Region fromRegion;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "to_region")
    private Region toRegion;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "from_city")
    private City fromCity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "to_city")
    private City toCity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @PrimaryKeyJoinColumn(name = "created_by")
    private UserEntity createdBy;

    @Column(name = "from_latitude")
    private double fromLatitude;

    @Column(name = "from_longitude")
    private double fromLongitude;

    @Column(name = "to_longitude")
    private double toLongitude;

    @Column(name = "to_latitude")
    private double toLatitude;

    @Column(name = "active")
    private boolean active;

    @Column(name = "deleted")
    private boolean deleted;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @Column(name = "time_to_send")
    private LocalDateTime timeToSend;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "info")
    private String info;

    @Column(name = "price")
    private double price;

    @OneToMany
    private List<Attachment> photos;

    public static AnnouncementClient toEntity(AnnouncementClientCreateDto announcementRequestDto) {
        return AnnouncementClient.builder()
                .fromLatitude(announcementRequestDto.getFromLatitude())
                .fromLongitude(announcementRequestDto.getFromLongitude())
                .toLatitude(announcementRequestDto.getToLatitude())
                .toLongitude(announcementRequestDto.getToLongitude())
                .info(announcementRequestDto.getInfo())
                .createdAt(LocalDateTime.now())
                .price(announcementRequestDto.getPrice())
                .active(true)
                .deleted(false)
                .build();
    }
}
