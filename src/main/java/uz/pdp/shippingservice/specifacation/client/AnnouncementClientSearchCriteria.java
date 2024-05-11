package uz.pdp.shippingservice.specifacation.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import uz.pdp.shippingservice.entity.locations.City;
import uz.pdp.shippingservice.entity.locations.Country;
import uz.pdp.shippingservice.entity.locations.Region;
import uz.pdp.shippingservice.entity.user.UserEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AnnouncementClientSearchCriteria {


    private Integer fromCountryId;

    private Integer toCountryId;

    private Integer fromRegionId;

    private Integer toRegionId;

    private Integer fromCityId;

    private Integer toCityId;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String timeToSendFrom;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String timeToSendTo;

}
