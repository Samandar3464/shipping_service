package uz.pdp.shippingservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("services.sms-service")
@Data
public class SmsServiceProperties {

    private String username;
    private String password;
    private String url;
}
