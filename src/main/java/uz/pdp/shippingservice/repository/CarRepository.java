package uz.pdp.shippingservice.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Car;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, Integer> {

//     Optional<Car> findByUserIdAndActiveTrue(Integer user_id);

    Page<Car> findAllByActiveFalse(Pageable pageable);
}
