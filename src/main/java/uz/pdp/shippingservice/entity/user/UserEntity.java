package uz.pdp.shippingservice.entity.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.shippingservice.dto.user.UserRegisterDto;
import uz.pdp.shippingservice.dto.user.UserUpdateDto;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.enums.Gender;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 13,max = 13)
    @Column(name = "phone" ,unique = true)
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Size(min = 6)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "created_at")
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @Column(name = "blocked")
    private boolean blocked;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "firebase_token" ,columnDefinition="TEXT")
    private String firebaseToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "avatar_id")
    private Attachment avatar;


    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @Column(name = "role")
    private List<UserRole> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        role.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return blocked;
    }

    public static UserEntity toEntity(UserRegisterDto dto){
        return UserEntity.builder()
                .phone(dto.getPhone())
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .blocked(false)
                .build();
    }

    public static void update(UserUpdateDto dto , UserEntity entity){
        entity.setName(dto.getName());
        entity.setFatherName(dto.getFatherName());
        entity.setSurname(dto.getSurname());
        entity.setGender(dto.getGender());
    }
}
