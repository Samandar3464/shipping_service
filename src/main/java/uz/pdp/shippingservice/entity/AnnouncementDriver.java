package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.announcementDriver.AnnouncementDriverDto;
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

     @Column(name = "is_active")
     private boolean isActive;

     @Column(name = "is_deleted")
     private boolean isDeleted;

     @Column(name = "can_go_another_region")
     private boolean canGoAnotherRegion;

     @Column(name = "can_go_another_country")
     private boolean canGoAnotherCountry;

     @Column(name = "time_to_drive")
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime timeToDrive;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @Column(name = "country_id")
     private Country country;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @Column(name = "region_id")
     private Region region;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @Column(name = "city_id")
     private City city;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @Column(name = "user_entity_id")
     private UserEntity userEntity;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     @Column(name = "driver_entity_id")
     private DriverEntity driverEntity;

     @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
     @Column(name = "created_at")
     private LocalDateTime createdAt;

     public static AnnouncementDriver from(AnnouncementDriverDto dto) {
          return AnnouncementDriver.builder()
                  .currentLatitude(dto.getCurrentLatitude())
                  .currentLongitude(dto.getCurrentLongitude())
                  .info(dto.getInfo())
                  .canGoAnotherRegion(dto.isGoAnotherRegion())
                  .canGoAnotherCountry(dto.isGoAnotherCountry())
                  .createdAt(LocalDateTime.now())
                  .isActive(true)
                  .isDeleted(false)
                  .build();
     }



}
