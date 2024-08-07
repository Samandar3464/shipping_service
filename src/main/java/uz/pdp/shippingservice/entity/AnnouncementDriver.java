package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverCreateDto;
import uz.pdp.shippingservice.entity.locations.City;
import uz.pdp.shippingservice.entity.locations.Country;
import uz.pdp.shippingservice.entity.locations.Region;
import uz.pdp.shippingservice.entity.user.DriverEntity;
import uz.pdp.shippingservice.entity.user.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "announcement_driver")
public class AnnouncementDriver {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     @Column(name = "current_latitude")
     private double currentLatitude;

     @Column(name = "current_longitude")
     private double currentLongitude;

     @Column(name = "info")
     private String info;

     @Column(name = "active")
     private boolean active;

     @Column(name = "deleted")
     private boolean deleted;

     @Column(name = "can_go_another_country")
     private boolean canGoAnotherCountry;

     @Column(name = "can_go_another_region")
     private boolean canGoAnotherRegion;

     @Column(name = "only_city")
     private boolean onlyCity;

     @Column(name = "time_to_drive")
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime timeToDrive;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @JoinColumn(name = "country_id")
     private Country country;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @JoinColumn(name = "region_id")
     private Region region;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @JoinColumn(name = "city_id")
     private City city;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @JoinColumn(name = "created_by_id")
     private UserEntity createdBy;

     @ManyToOne
     @JsonIgnore
     @OnDelete(action = OnDeleteAction.CASCADE)
     @JoinColumn(name = "created_driver_id")
     private DriverEntity createdDriver;

     @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
     @Column(name = "created_at")
     private LocalDateTime createdAt;

     public static AnnouncementDriver toEntity(AnnouncementDriverCreateDto dto) {
          return AnnouncementDriver.builder()
                  .currentLatitude(dto.getCurrentLatitude())
                  .currentLongitude(dto.getCurrentLongitude())
                  .info(dto.getInfo())
                  .canGoAnotherRegion(dto.isGoAnotherRegion())
                  .canGoAnotherCountry(dto.isGoAnotherCountry())
                  .createdAt(LocalDateTime.now())
                  .active(true)
                  .deleted(false)
                  .build();
     }



}
