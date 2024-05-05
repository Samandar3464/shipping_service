package uz.pdp.shippingservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.user.DriverEntity;

public interface DriverInfoRepository extends JpaRepository<DriverEntity, Long> {

    Page<DriverEntity> findAllByActiveIsFalseOrderByCreatedAtDesc(Pageable pageable);


}
