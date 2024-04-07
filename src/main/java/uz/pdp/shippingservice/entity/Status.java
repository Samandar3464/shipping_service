package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.model.request.StatusDto;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long stars;

    private Long count;

    public Status(int stars, int count) {
        this.stars = (long) stars;
        this.count = (long) count;
    }

    public static Status from(StatusDto statusDto, Status status) {
        return Status.builder()
                .id(status.getId())
                .stars(status.getStars() + statusDto.getStars())
                .count(status.getCount() + 1)
                .build();
    }
}
