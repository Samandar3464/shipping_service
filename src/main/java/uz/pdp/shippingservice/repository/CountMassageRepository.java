package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.CountMassage;
import java.time.LocalDateTime;

public interface CountMassageRepository extends JpaRepository<CountMassage, Integer> {
    Integer countAllByCount(int count);

    Integer countAllBySandedTimeBetween(LocalDateTime sandedTime, LocalDateTime sandedTime2);

}
