package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Region;

import java.util.Collection;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByName(String name);
    boolean existsByIdAndName(Integer id, String name);
    boolean existsByNameIn(Collection<String> name);
}
