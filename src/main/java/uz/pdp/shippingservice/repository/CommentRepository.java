package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments ,Integer> {
        Optional<Comments> findByPhoneNumber(String phoneNumber);

        List<Comments> findAllByReadFalse();
}
