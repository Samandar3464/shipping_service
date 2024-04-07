package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String carNumber;

    private String color;

    private String model;

    private float maximumLoad;

    private float maximumLength;

    private float maximumLoadWidth;

    private String texPassportNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Attachment> carPhotos;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment texPassportPhoto;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment photoDriverLicense;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private boolean active;

    public static Car from(CarRegisterRequestDto carRegisterRequestDto) {
        return Car.builder()
                .color(carRegisterRequestDto.getColor())
                .model(carRegisterRequestDto.getModel())
                .maximumLoad(carRegisterRequestDto.getMaximumLoad())
                .maximumLength(carRegisterRequestDto.getMaximumLength())
                .maximumLoadWidth(carRegisterRequestDto.getMaximumLoadWidth())
                .texPassportNumber(carRegisterRequestDto.getTexPassportNumber())
                .carNumber(carRegisterRequestDto.getCarNumber())
                .active(true)
                .build();
    }

}
