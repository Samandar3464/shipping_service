package uz.pdp.shippingservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.Status;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {

}
