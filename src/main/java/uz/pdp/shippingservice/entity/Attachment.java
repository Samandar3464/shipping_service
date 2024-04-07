package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attachment  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String originName;

    private long size;

    private String newName;

    private String type;

    private String contentType;

    private String path;

//    @ManyToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Car car;
}
