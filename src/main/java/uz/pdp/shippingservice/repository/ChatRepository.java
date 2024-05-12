package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Chat;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
   }
