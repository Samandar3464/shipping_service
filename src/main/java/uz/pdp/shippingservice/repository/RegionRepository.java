package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.locations.Region;

import java.util.Collection;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByNameAndCountryId(String name , Integer id);
    boolean existsByIdAndName(Integer id, String name);
    boolean existsByNameIn(Collection<String> name);
}
