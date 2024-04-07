package uz.pdp.shippingservice.exception;

public class SmsServiceBroken extends RuntimeException {
  public SmsServiceBroken(String s){
        super(s);
    }
}
