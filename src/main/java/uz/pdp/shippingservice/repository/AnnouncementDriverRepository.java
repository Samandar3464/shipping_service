package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.AnnouncementDriver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnouncementDriverRepository extends JpaRepository<AnnouncementDriver, UUID> {

    List<AnnouncementDriver> findAllByActiveTrueAndDeletedFalseAndTimeToDriveIsBefore(LocalDateTime timeToDrive);

    boolean existsByDeletedFalseAndId(UUID id);
}
