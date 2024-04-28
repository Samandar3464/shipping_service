package uz.pdp.shippingservice.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sms")
public class SmsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 13, max = 13)
    @Column(name = "user_name", unique = true)
    private String phone;

    @Column(name = "message")
    private String message;

    @Column(name = "code")
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    public static SmsEntity toEntity(String phone, String message , String code) {
        return SmsEntity.builder().phone(phone).message(message).createdAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now().plusMinutes(3)).build();
    }
}
