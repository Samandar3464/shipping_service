package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementClientRepository extends JpaRepository<AnnouncementClient, UUID> {

    List<AnnouncementClient> findAllByFromRegionIdAndFromCityIdAndTimeToSendBetweenOrderByCreatedTimeDesc(Integer fromRegion_id, Integer fromCity_id, LocalDateTime timeToSend, LocalDateTime timeToSend2);
    Optional<AnnouncementClient> findByIdAndActiveAndDeletedFalse(UUID id, boolean active);

    Optional<AnnouncementClient> findByIdAndDeletedFalse(UUID id);

//    List<AnnouncementClient> findAllByUserIdAndDeletedFalseOrderByCreatedTime(Long id);
//    List<AnnouncementClient> findAllByUserIdAndActiveAndDeletedFalseOrderByCreatedTimeDesc(Long id, boolean active);
}
