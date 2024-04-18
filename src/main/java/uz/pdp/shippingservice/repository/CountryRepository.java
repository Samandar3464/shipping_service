package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.locations.Country;

public interface CountryRepository extends JpaRepository<Country ,Integer> {
    boolean existsByName(String name);
    boolean existsByIdAndName(Integer id, String name);
}
