package uz.pdp.shippingservice.exception;

public class CdnServerNotFoundException extends RuntimeException{
   public CdnServerNotFoundException(String message){
        super(message);
    }
}
