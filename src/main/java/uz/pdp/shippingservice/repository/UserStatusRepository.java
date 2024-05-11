package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.UserStatus;
import uz.pdp.shippingservice.enums.Type;

import java.util.List;

public interface UserStatusRepository extends JpaRepository<UserStatus,Long> {

        List<UserStatus> findAllByGivenToIdAndType(Long givenTo_id, Type type);
}
