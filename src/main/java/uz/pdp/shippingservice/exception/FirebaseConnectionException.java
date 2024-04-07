package uz.pdp.shippingservice.exception;

public class FirebaseConnectionException extends RuntimeException {
    public FirebaseConnectionException(String firebaseException) {
        super(firebaseException);
    }
}
