package uz.pdp.shippingservice.exception;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(String massage) {
        super(massage);
    }
}
