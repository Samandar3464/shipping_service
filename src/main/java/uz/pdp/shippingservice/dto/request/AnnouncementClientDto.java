package uz.pdp.shippingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementClientDto {

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

     @JsonSerialize(using = LocalDateTimeSerializer.class)
     @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
     private LocalDateTime timeToSend;
}
