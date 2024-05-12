package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.locations.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country ,Integer> {
    boolean existsByName(String name);
    Optional<Country> findByIdAndName(Integer id, String name);
    Optional<Country> findByIdAndActiveTrue(Integer id);

    List<Country> findAllByActiveTrue();
}
