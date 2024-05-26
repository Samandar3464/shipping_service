package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Chat;
import uz.pdp.shippingservice.enums.TypeClients;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

   Optional<Chat> findFirstByTypeClientAndAnnouncementIdAndReceiverIdAndAndSenderId(TypeClients typeClient , UUID announcementId , Long receiverId , Long senderId);

   Optional<Chat> findFirstById(UUID id);
   }
