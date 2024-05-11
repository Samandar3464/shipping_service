package uz.pdp.shippingservice.dto.announcementClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementClientCreateDto {

     private Integer fromCountryId;
     private Integer toCountryId;
     private Integer fromRegionId;
     private Integer toRegionId;
     private Integer fromCityId;
     private Integer toCityId;
     private double fromLatitude;
     private double fromLongitude;
     private double toLongitude;
     private double toLatitude;
     private double price;
     private String info;
     @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
     private String timeToSend;

     private List<MultipartFile> photos;
}
