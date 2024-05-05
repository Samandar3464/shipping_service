package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.dto.request.CarRegisterRequestDto;
import uz.pdp.shippingservice.entity.user.UserEntity;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
//    @PrimaryKeyJoinColumn(name = "")
    private Attachment texPassportPhoto;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment photoDriverLicense;

    @JsonIgnore
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    private boolean isActive;

    public static Car from(CarRegisterRequestDto carRegisterRequestDto) {
        return Car.builder()
                .color(carRegisterRequestDto.getColor())
                .model(carRegisterRequestDto.getModel())
                .maximumLoad(carRegisterRequestDto.getMaximumLoad())
                .maximumLength(carRegisterRequestDto.getMaximumLength())
                .maximumLoadWidth(carRegisterRequestDto.getMaximumLoadWidth())
                .texPassportNumber(carRegisterRequestDto.getTexPassportNumber())
                .carNumber(carRegisterRequestDto.getCarNumber())
                .isActive(true)
                .build();
    }

}
