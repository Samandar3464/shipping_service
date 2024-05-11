package uz.pdp.shippingservice.dto.announcementDriver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AnnouncementDriverResponse {

    private UUID id;

    private double currentLatitude;

    private double currentLongitude;

    private String info;

    private boolean canGoAnotherCountry;

    private boolean canGoAnotherRegion;

    private boolean onlyCity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeToDrive;

    private String countryName;

    private String regionName;

    private String cityName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    private Long userId;

    private String phone;

    private String fullname;

    private Long driverId;

    private String model;

    private float carLength;

    private float carWidth;

    private float maxLoad;

    private boolean hasFreezer;

    private boolean hasWrappedFully;

    public AnnouncementDriverResponse(@JsonProperty("id") UUID id,
                                      @JsonProperty("current_latitude") double currentLatitude,
                                      @JsonProperty("current_longitude") double currentLongitude,
                                      @JsonProperty("info") String info,
                                      @JsonProperty("can_go_another_country") boolean canGoAnotherCountry,
                                      @JsonProperty("can_go_another_region") boolean canGoAnotherRegion,
                                      @JsonProperty("only_city") boolean onlyCity,
                                      @JsonProperty("time_to_drive") Timestamp timeToDrive,
                                      @JsonProperty("country_name") String countryName,
                                      @JsonProperty("region_name") String regionName,
                                      @JsonProperty("city_name") String cityName,
                                      @JsonProperty("created_at") Timestamp createdAt,
                                      @JsonProperty("user_id") Long userId,
                                      @JsonProperty("phone") String phone,
                                      @JsonProperty("fullname") String fullname,
                                      @JsonProperty("driver_id") Long driverId,
                                      @JsonProperty("model") String model,
                                      @JsonProperty("car_length") float carLength,
                                      @JsonProperty("car_width") float carWidth,
                                      @JsonProperty("max_load") float maxLoad,
                                      @JsonProperty("has_freezer") boolean hasFreezer,
                                      @JsonProperty("has_wrapped_fully") boolean hasWrappedFully) {
        this.id = id;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.info = info;
        this.canGoAnotherCountry = canGoAnotherCountry;
        this.canGoAnotherRegion = canGoAnotherRegion;
        this.onlyCity = onlyCity;
        this.timeToDrive = timeToDrive.toLocalDateTime();
        this.countryName = countryName;
        this.regionName = regionName;
        this.cityName = cityName;
        this.createdAt = createdAt.toLocalDateTime();
        this.userId = userId;
        this.phone = phone;
        this.fullname = fullname;
        this.driverId = driverId;
        this.model = model;
        this.carLength = carLength;
        this.carWidth = carWidth;
        this.maxLoad = maxLoad;
        this.hasFreezer = hasFreezer;
        this.hasWrappedFully = hasWrappedFully;
    }
}
