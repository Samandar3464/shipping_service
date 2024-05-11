package uz.pdp.shippingservice.dto.announcementDriver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

     private boolean goAnotherCountry;

     private boolean goAnotherRegion;

     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private String timeToDrive;


}
