package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.SmsServiceTokenEntity;

public interface SmsServiceTokenRepository extends JpaRepository<SmsServiceTokenEntity, Integer> {
}
