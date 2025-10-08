package ws.beauty.salon.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceCategoryRequest {

    @NotBlank
    @Size(max = 50)
    private String categoryName;

    @Size(max = 500)
    private String description;
}