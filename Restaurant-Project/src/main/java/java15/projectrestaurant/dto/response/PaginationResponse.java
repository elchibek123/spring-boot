package java15.projectrestaurant.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PaginationResponse<T> {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List<T> content;

    public PaginationResponse<T> setValuesTo(Page<T> page) {
        this.pageNumber = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.content = page.getContent();
        return this;
    }
}
