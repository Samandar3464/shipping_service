package uz.pdp.shippingservice.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String name){
        super(name);
    }
}
