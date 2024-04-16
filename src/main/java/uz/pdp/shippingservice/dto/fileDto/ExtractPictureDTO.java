package uz.pdp.shippingservice.dto.fileDto;

import lombok.Data;

@Data
public class ExtractPictureDTO {
    private String filename;
    private byte[] bytes;
}
