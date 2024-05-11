package uz.pdp.shippingservice.dto.announcementClient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementClient;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AnnouncementClientResponse implements Serializable {
    private UUID id;
    private String fromCountry;
    private String toCountry;
    private String fromRegion;
    private String toRegion;
    private String fromCity;
    private String toCity;

    private double fromLatitude;
    private double fromLongitude;
    private double toLatitude;
    private double toLongitude;

    private String info;
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime timeToSend;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
    private String createdUserFullname;
    private String phone;
    private Long userId;

    private List<String> photos;

    public AnnouncementClientResponse(@JsonProperty ("id") UUID id,
                                      @JsonProperty ("from_country_name") String fromCountry,
                                      @JsonProperty ("to_country_name") String toCountry,
                                      @JsonProperty ("from_region_name") String fromRegion,
                                      @JsonProperty ("to_region_name") String toRegion,
                                      @JsonProperty ("from_city_name") String fromCity,
                                      @JsonProperty ("to_city_name") String toCity,
                                      @JsonProperty ("from_latitude") double fromLatitude,
                                      @JsonProperty ("from_longitude") double fromLongitude,
                                      @JsonProperty ("to_latitude") double toLatitude,
                                      @JsonProperty ("to_longitude") double toLongitude,
                                      @JsonProperty ("info") String info,
                                      @JsonProperty ("price") double price,
                                      @JsonProperty ("time_to_send") Timestamp timeToSend,
                                      @JsonProperty ("created_at") Timestamp createdAt,
                                      @JsonProperty ("fullname") String createdUserFullname,
                                      @JsonProperty ("phone") String phone,
                                      @JsonProperty ("user_id") Long userId,
                                      @JsonProperty ("photos") List<String> photos) {
        this.id = id;
        this.fromCountry = fromCountry;
        this.toCountry = toCountry;
        this.fromRegion = fromRegion;
        this.toRegion = toRegion;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.fromLatitude = fromLatitude;
        this.fromLongitude = fromLongitude;
        this.toLatitude = toLatitude;
        this.toLongitude = toLongitude;
        this.info = info;
        this.price = price;
        this.timeToSend = timeToSend.toLocalDateTime();
        this.createdAt = createdAt.toLocalDateTime();
        this.createdUserFullname = createdUserFullname;
        this.phone = phone;
        this.userId = userId;
        this.photos = photos;
    }
}
