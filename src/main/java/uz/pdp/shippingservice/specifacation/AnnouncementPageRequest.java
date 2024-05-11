package uz.pdp.shippingservice.specifacation;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class AnnouncementPageRequest {
    private int pageNumber = 0;
    private int pageSize = 20;
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    private String sortBy = "created_at";
}
