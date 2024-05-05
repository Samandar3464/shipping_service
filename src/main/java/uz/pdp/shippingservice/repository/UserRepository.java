package uz.pdp.shippingservice.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhone(@NotBlank @Size(min = 13, max = 13) String phone);

    boolean existsByPhone(@NotBlank @Size(min = 13, max = 13) String phone);

}
