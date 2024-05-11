package uz.pdp.shippingservice.specifacation.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class AnnouncementDriverSearchCriteria {


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
