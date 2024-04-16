package uz.pdp.shippingservice.dto.fileDto;

import lombok.Data;

@Data
public class ServerCdnDTO {
    String host;
    String username;
    String password;
    String uploadPath;
    String publicPath;
    String alias;
    int port;
}