package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.UserRole;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByName(String name);
    Boolean existsByName(String name);
    List<UserRole> findAllByName(String name);

}
