package uz.pdp.shippingservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import uz.pdp.shippingservice.dto.fileDto.ServerCdnDTO;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@ConfigurationProperties("cdn")
@Data
public class CdnProperties {
    HashMap<String, ServerCdnDTO> servers;
    ArrayList<String> inRotation;
}
