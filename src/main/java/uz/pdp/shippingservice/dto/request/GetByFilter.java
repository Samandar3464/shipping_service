package uz.pdp.shippingservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetByFilter {

    private Integer countryId;

    private Integer regionId;

    private Integer cityId;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private String fromTime;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private String toTime;

}
