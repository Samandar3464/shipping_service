package uz.pdp.shippingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetByFilter {
    private Integer countryId;

    private Integer regionId;

    private Integer cityId;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private String time1;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private String time2;

}
