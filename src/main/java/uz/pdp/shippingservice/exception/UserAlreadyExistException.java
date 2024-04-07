package uz.pdp.shippingservice.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String userNotFound) {
    }
}
