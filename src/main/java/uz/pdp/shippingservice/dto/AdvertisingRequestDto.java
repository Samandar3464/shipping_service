package uz.pdp.shippingservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisingRequestDto {

    private double price;

    private String name;

    private String ownerData;

    private String url;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String startDate;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String endDate;

    private MultipartFile content;
}
