package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.UserStatus;
import uz.pdp.shippingservice.enums.TypeClients;

import java.util.List;

public interface UserStatusRepository extends JpaRepository<UserStatus,Long> {

        List<UserStatus> findAllByGivenToIdAndTypeClient(Long givenTo_id, TypeClients typeClients);
}
