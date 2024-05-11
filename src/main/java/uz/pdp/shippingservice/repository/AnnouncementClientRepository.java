package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementClientRepository extends JpaRepository<AnnouncementClient, UUID> {

    List<AnnouncementClient> findAllByCreatedByIdAndActiveTrueAndDeletedFalse(Long createdBy_id);

    Optional<AnnouncementClient> findByIdAndActiveTrueAndDeletedFalse(UUID id);

    Optional<AnnouncementClient> findByIdAndDeletedFalse(UUID id);

    List<AnnouncementClient> findAllByDeletedFalseAndActiveTrueAndTimeToSendBefore(LocalDateTime timeToSend);

}
