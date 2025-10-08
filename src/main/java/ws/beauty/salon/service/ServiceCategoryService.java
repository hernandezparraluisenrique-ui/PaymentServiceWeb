package ws.beauty.salon.service;

import java.util.List;

import ws.beauty.salon.dto.ServiceCategoryRequest;
import ws.beauty.salon.dto.ServiceCategoryResponse;

public interface ServiceCategoryService {
    List<ServiceCategoryResponse> findAll();

    ServiceCategoryResponse findById(Long idCategory);

    ServiceCategoryResponse create(ServiceCategoryRequest req);

    ServiceCategoryResponse update(Long idCategory, ServiceCategoryRequest req);

    void delete(Long idCategory);
}
