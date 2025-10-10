package ws.beauty.salon.dto;

import lombok.Data;

@Data
public class ServiceCategoryRequestDTO {
    private Integer idCategory;
    private String categoryName;
    private String description;
}
