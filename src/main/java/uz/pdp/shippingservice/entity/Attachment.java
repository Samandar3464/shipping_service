package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


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

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @UpdateTimestamp
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "deleted")
    private Boolean deleted = false;

}
