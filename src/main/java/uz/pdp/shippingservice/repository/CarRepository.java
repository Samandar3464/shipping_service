package uz.pdp.shippingservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
     List<Car> findAllByActiveFalse();
     Optional<Car> findByUserIdAndActiveTrue(Integer user_id);
     Optional<Car> findFirstByUserIdAndActiveTrue(Integer user_id);

    Page<Car> findAllByActiveFalse(Pageable pageable);
}
