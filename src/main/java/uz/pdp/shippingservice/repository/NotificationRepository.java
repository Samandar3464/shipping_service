package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Notification;
import uz.pdp.shippingservice.enums.NotificationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllBySenderIdAndDeletedFalseAndNotificationTypeOrderByCreatedTimeDesc(Integer senderId, NotificationType notificationType);

    List<Notification> findAllByReceiverIdAndActiveAndReceivedAndDeletedFalseAndNotificationTypeOrderByCreatedTime(Integer id, boolean b, boolean b1, NotificationType notificationType);

    List<Notification> findAllBySenderIdAndReceivedTrueAndNotificationTypeOrderByCreatedTime(Integer id, NotificationType notificationType);

    List<Notification> findAllByReceiverIdAndReceivedTrueAndNotificationTypeOrderByCreatedTime(Integer id, NotificationType notificationType);

    Optional<Notification> findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(Integer id, Integer id1, UUID announcementId);

//    List<Notification> findAllByReceiverIdAndReceivedTrueAndNotificationTypeOrderByCreatedTime(UUID id, NotificationType notificationType);

//    Optional<Notification> findByIdAndActive(UUID id, boolean active);
//
//    List<Notification> findAllBySenderIdAndActiveAndReceivedAndNotificationType(UUID senderId, boolean active, boolean received, NotificationType notificationType);
//
//    List<Notification> findAllByReceiverIdAndActiveAndReceivedAndNotificationTypeOrderByCreatedTimeDesc(UUID receiverId, boolean active, boolean received, NotificationType notificationType);
//    Optional<Notification> findFirstBySenderIdAndReceiverIdAndAnnouncementPassengerIdAndActiveTrueAndReceivedFalseOrderByCreatedTimeDesc(UUID senderId, UUID receiverId, UUID announcementPassengerId);
//    List<Notification> findByAnnouncementDriverIdAndActiveAndReceived(UUID announcementId, boolean active, boolean received);
//    List<Notification> findAllByReceiverIdAndReceivedTrueAndNotificationTypeOrderByCreatedTimeDesc(UUID receiverId, NotificationType notificationType);
//    List<Notification> findAllBySenderIdAndReceivedTrueAndNotificationTypeOrderByCreatedTimeDesc(UUID receiverId, NotificationType notificationType);
//List<Notification> findAllBySenderIdOrReceiverIdAndNotificationTypeAndReceivedTrueOrderByCreatedTime(UUID senderId, UUID receiverId, NotificationType notificationType);
}
