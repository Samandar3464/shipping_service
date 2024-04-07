package uz.pdp.shippingservice.exception;

public class RefreshTokeNotFound extends RuntimeException {
    public RefreshTokeNotFound(String reFreshTokenNotFound) {
        super(reFreshTokenNotFound);
    }
}
