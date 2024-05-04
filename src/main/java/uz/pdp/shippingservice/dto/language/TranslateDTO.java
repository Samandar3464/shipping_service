package uz.pdp.shippingservice.dto.language;

import lombok.Data;

@Data
public class TranslateDTO {
    String locale;
    String key;
    String translation;
}
