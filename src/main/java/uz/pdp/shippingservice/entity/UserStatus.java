package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.request.UserStatusDto;
import uz.pdp.shippingservice.entity.user.UserEntity;
import uz.pdp.shippingservice.enums.Type;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_status")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "starts")
    private Integer stars;

    @Column(columnDefinition="TEXT" , name = "text")
    private String text;

    @ManyToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn(name = "given_to_id")
    private UserEntity givenTo;

    @OneToOne
    @JsonIgnore
    @PrimaryKeyJoinColumn(name = "given_by_id")
    private UserEntity givenBy;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static UserStatus toEntity(UserStatusDto dto){
        return UserStatus.builder()
                .type(dto.getType())
                .createdAt(LocalDateTime.now())
                .stars(dto.getStars())
                .text(dto.getText())
                .build();
    }
}
