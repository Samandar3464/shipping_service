package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementDriver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementDriverRepository extends JpaRepository<AnnouncementDriver, UUID> {

//    List<AnnouncementDriver> findAllByCountryIdOrderByCreatedAtDesc(Integer CountryId);
//    List<AnnouncementDriver> findAllByRegionIdOrderByCreatedAtDesc(Integer currentRegionId);
//    List<AnnouncementDriver> findAllByCityIdOrderByCreatedAtDesc(Integer currentCityId);
//    Optional<AnnouncementDriver> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);
//    Optional<AnnouncementDriver> findByIdAndDeletedFalse(UUID id);
}
