package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String phoneNumber;

    @Column(columnDefinition="TEXT")
    private String text;

    private boolean read;
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime createdTime;
}
