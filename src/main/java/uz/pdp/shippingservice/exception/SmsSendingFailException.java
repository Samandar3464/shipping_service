package uz.pdp.shippingservice.exception;

public class SmsSendingFailException extends RuntimeException {
    public SmsSendingFailException(String massage) {
        super(massage);
    }
}
