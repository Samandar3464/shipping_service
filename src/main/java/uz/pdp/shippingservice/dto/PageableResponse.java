package uz.pdp.shippingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T>{

    private List<T> content;
    private int currentPage;
    private int totalPages;
    private long totalCount;

}
