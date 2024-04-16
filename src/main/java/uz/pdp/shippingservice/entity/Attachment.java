package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attachment  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "size")
    private long size;

    @Column(name = "new_name")
    private String newName;

    @Column(name = "type")
    private String type;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "path")
    private String path;

}
