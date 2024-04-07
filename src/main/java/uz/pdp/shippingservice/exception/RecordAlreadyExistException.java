package uz.pdp.shippingservice.exception;

public class RecordAlreadyExistException extends RuntimeException{
    public RecordAlreadyExistException(String name){
        super(name);
    }
}
