package uz.pdp.shippingservice.dto.announcementDriver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDriverDto {
     private Integer countryId;

     private Integer regionId;

     private Integer cityId;

     private double currentLatitude;

     private double currentLongitude;

     private String info;

     private boolean goAnotherRegion;


}
