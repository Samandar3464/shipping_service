package uz.pdp.shippingservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
     public TokenResponse(String accessToken) {
          this.accessToken=accessToken;
     }
}
