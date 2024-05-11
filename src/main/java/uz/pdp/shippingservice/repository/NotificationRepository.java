package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Notification;
import uz.pdp.shippingservice.enums.Type;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllBySenderIdAndDeletedFalseAndTypeOrderByCreatedTimeDesc(Long senderId, Type type);

    List<Notification> findAllByReceiverIdAndActiveAndReceivedAndDeletedFalseAndTypeOrderByCreatedTime(Long id, boolean b, boolean b1, Type type);

    List<Notification> findAllBySenderIdAndReceivedTrueAndTypeOrderByCreatedTime(Long id, Type type);

    List<Notification> findAllByReceiverIdAndReceivedTrueAndTypeOrderByCreatedTime(Long id, Type type);

    Optional<Notification> findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(Long id, Long id1, UUID announcementId);
}
