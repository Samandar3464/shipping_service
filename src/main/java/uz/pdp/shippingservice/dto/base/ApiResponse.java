package uz.pdp.shippingservice.dto.base;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse implements Serializable {

    private String message;

    private boolean status;

    private Object data;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public ApiResponse(Object data, boolean status) {
        this.status = status;
        this.data = data;
    }
}
