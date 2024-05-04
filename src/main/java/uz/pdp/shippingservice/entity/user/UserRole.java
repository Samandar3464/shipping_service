package uz.pdp.shippingservice.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.dto.request.UserRoleDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private boolean deleted;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    public static UserRole toEntity(UserRoleDto dto){
        return UserRole.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
