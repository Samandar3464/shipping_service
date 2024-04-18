package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {
    UserRole findByName(String name);

}
