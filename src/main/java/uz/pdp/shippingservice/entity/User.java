package uz.pdp.shippingservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.pdp.shippingservice.enums.Gender;
import uz.pdp.shippingservice.model.request.UserRegisterDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Size(min = 9,max = 9)
    private String phone;

    @NotBlank
    @Size(min = 6)
    private String password;

    private LocalDate birthDate;

    private LocalDateTime registeredDate;

    private boolean isBlocked;

    private String fireBaseToken;

    private Integer verificationCode;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private Attachment profilePhoto;

    @OneToOne(cascade = CascadeType.ALL)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        roles.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
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

    public static User from(UserRegisterDto userRegisterDto){
        return User.builder()
                .fullName(userRegisterDto.getFullName())
                .phone(userRegisterDto.getPhone())
                .gender(userRegisterDto.getGender())
                .registeredDate(LocalDateTime.now())
                .status(userRegisterDto.getStatus())
                .isBlocked(true)
                .build();
    }
}
