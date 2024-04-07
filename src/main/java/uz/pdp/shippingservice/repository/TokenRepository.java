package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.FireBaseToken;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}
