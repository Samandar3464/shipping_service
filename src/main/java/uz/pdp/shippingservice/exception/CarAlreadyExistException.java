package uz.pdp.shippingservice.exception;

public class CarAlreadyExistException extends RuntimeException {
    public CarAlreadyExistException(String s) {
        super(s);
    }
}
