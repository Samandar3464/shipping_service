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

     private double currentLatitude;

     private double currentLongitude;

     private String info;

     private boolean active;

     private boolean deleted;

     private boolean goAnotherRegion;
     @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
     private LocalDateTime createdTime;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     private Country country;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     private Region region;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     private City city;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     private UserEntity userEntity;

//     @ManyToOne
//     @OnDelete(action = OnDeleteAction.CASCADE)
//     private Car car;

     public static AnnouncementDriver from(AnnouncementDriverDto announcementRequestDto) {
          return AnnouncementDriver.builder()
                  .currentLatitude(announcementRequestDto.getCurrentLatitude())
                  .currentLongitude(announcementRequestDto.getCurrentLongitude())
                  .info(announcementRequestDto.getInfo())
                  .createdTime(LocalDateTime.now())
                  .goAnotherRegion(announcementRequestDto.isGoAnotherRegion())
                  .active(true)
                  .deleted(false)
                  .build();
     }



}
