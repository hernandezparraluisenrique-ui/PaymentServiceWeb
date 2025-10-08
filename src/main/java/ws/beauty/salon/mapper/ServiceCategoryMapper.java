package ws.beauty.salon.mapper;

import ws.beauty.salon.dto.ServiceCategoryRequest;
import ws.beauty.salon.dto.ServiceCategoryResponse;
import ws.beauty.salon.model.ServiceCategory;

public final class ServiceCategoryMapper {

    private ServiceCategoryMapper() {
        // Evita instanciación
    }

    public static ServiceCategoryResponse toResponse(ServiceCategory category) {
        if (category == null)
            return null;

        return ServiceCategoryResponse.builder()
                .idCategory(category.getIdCategory())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .build();
    }

    public static ServiceCategory toEntity(ServiceCategoryRequest dto) {
        if (dto == null)
            return null;

        return ServiceCategory.builder()
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .build();
    }

    public static void copyToEntity(ServiceCategoryRequest dto, ServiceCategory entity) {
        if (dto == null || entity == null)
            return;

        entity.setCategoryName(dto.getCategoryName());
        entity.setDescription(dto.getDescription());
    }
}
