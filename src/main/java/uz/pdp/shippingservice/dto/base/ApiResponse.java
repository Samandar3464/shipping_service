package uz.pdp.shippingservice.dto.base;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

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
