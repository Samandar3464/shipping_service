package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.Car;


import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDto {

     private UUID id;

     private String carNumber;

     private String color;

     private String texPassport;

     private String autoModel;

     private List<String> autoPhotosPath;

     private String texPassportPhotoPath;

     private String photoDriverLicense;

     private boolean active;


     public static CarResponseDto from(Car car){
         return    CarResponseDto.builder()
                 .id(car.getId())
                 .carNumber(car.getCarNumber())
                 .color(car.getColor())
                 .texPassport(car.getTexPassportNumber())
                 .active(car.isActive())
                 .build();
     }
}
