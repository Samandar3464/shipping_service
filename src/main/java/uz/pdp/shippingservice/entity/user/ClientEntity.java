package uz.pdp.shippingservice.entity.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.shippingservice.dto.request.UserRegisterDto;
import uz.pdp.shippingservice.entity.Attachment;
import uz.pdp.shippingservice.entity.UserRole;
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
public class ClientEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "father_name")
    private String fatherName;

    @NotBlank
    @Size(min = 9,max = 9)
    @Column(name = "phone" ,unique = true)
    private String phone;

    @Size(min = 6)
    @Column(name = "password")
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "firebase_token")
    private String firebaseToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    @Column(name = "avatar_id")
    private Attachment avatarId;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    @Column(name = "roles")
    private List<UserRole> authroles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authroles.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
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
        return isBlocked;
    }

    public static ClientEntity from(UserRegisterDto userRegisterDto){
        return ClientEntity.builder()
                .name(userRegisterDto.getFullName())
                .phone(userRegisterDto.getPhone())
                .gender(userRegisterDto.getGender())
                .createdAt(LocalDateTime.now())
                .isBlocked(true)
                .build();
    }
}
