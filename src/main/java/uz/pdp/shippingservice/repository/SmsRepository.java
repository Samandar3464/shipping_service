package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.SmsEntity;

import java.util.Optional;

public interface SmsRepository extends JpaRepository<SmsEntity, Long> {

    Optional<SmsEntity> findByPhoneAndCode(String phone ,String code);

}
