package uz.pdp.shippingservice.dto.announcementDriver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDriverCreateDto {

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
