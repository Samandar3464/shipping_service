package uz.pdp.shippingservice.dto.fileDto;

import lombok.Data;

@Data
public class UploadedDTO {
    String uploadPath;
    String uploadFile;
    String extension;
    Long size;
    String name;
    ServerCdnDTO server;
}
