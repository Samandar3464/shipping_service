package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementDriver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementDriverRepository extends JpaRepository<AnnouncementDriver, UUID> {

    List<AnnouncementDriver> findAllByUserIdAndDeletedFalseOrderByCreatedTime(UUID user_id);
    List<AnnouncementDriver> findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID user_id, boolean active);
    List<AnnouncementDriver> findAllByCountryIdOrderByCreatedTimeDesc(Integer CountryId);
    List<AnnouncementDriver> findAllByRegionIdOrderByCreatedTimeDesc(Integer currentRegionId);
    List<AnnouncementDriver> findAllByCityIdOrderByCreatedTimeDesc(Integer currentCityId);
    boolean existsByUserIdAndActiveTrueAndDeletedFalse(UUID user_id);
    Optional<AnnouncementDriver> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);
    Optional<AnnouncementDriver> findByIdAndDeletedFalse(UUID id);
    Optional<AnnouncementDriver> findByIdAndUserId(UUID id, UUID user_id);

    Optional<AnnouncementDriver> findByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(UUID userId, boolean active);
}
