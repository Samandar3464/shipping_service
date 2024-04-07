package uz.pdp.shippingservice.exception;

public class CarNotFound extends RuntimeException {
    public CarNotFound(String name){
        super(name);
    }
}
