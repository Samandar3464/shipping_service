package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Advertising;

import java.util.List;
import java.util.UUID;

public interface AdvertisingRepository extends JpaRepository<Advertising,Long> {

    List<Advertising> findAllByActiveTrue();
}
