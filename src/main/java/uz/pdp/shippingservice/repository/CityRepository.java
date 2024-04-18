package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.locations.City;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

    boolean existsByNameAndRegionId(String name, Integer region_id);

    List<City> findAllByRegionId(Integer region_id);
}
