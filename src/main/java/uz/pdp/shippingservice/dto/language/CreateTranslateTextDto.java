package uz.pdp.shippingservice.dto.language;

import lombok.Data;

import java.util.HashMap;

@Data
public class CreateTranslateTextDto {

    private Integer id;
    private HashMap<LanguageEnum, String> translations;
}
