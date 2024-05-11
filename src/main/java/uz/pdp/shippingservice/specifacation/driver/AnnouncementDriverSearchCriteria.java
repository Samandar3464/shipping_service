package uz.pdp.shippingservice.specifacation.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class AnnouncementDriverSearchCriteria {


    private Integer countryId;

    private Integer regionId;

    private Integer cityId;

    private boolean canGoAnotherRegion;

    private boolean canGoAnotherCountry;

    private boolean onlyCity;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String timeToDriveFrom;

    @JsonFormat(pattern = "YYYY-MM-DD")
    private String timeToDriveTo;

}
