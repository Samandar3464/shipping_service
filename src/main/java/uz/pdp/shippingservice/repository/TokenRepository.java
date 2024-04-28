package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<FireBaseToken, Integer> {
}
