package uz.pdp.shippingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.shippingservice.entity.File;

public interface FileRepository extends JpaRepository<File ,Long> {
}
