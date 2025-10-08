package ws.beauty.salon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ws.beauty.salon.dto.ServiceCategoryRequest;
import ws.beauty.salon.dto.ServiceCategoryResponse;
import ws.beauty.salon.mapper.ServiceCategoryMapper;
import ws.beauty.salon.model.ServiceCategory;
import ws.beauty.salon.repository.ServiceCategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {

    private final ServiceCategoryRepository repository;

    @Override
    public List<ServiceCategoryResponse> findAll() {
        return repository.findAll().stream()
                .map(ServiceCategoryMapper::toResponse)
                .toList();
    }

    @Override
    public ServiceCategoryResponse findById(Long idCategory) {
        ServiceCategory category = repository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("ServiceCategory not found: " + idCategory));
        return ServiceCategoryMapper.toResponse(category);
    }

    @Override
    public ServiceCategoryResponse create(ServiceCategoryRequest request) {
        ServiceCategory saved = repository.save(ServiceCategoryMapper.toEntity(request));
        return ServiceCategoryMapper.toResponse(saved);
    }

    @Override
    public ServiceCategoryResponse update(Long idCategory, ServiceCategoryRequest dto) {
        ServiceCategory existing = repository.findById(idCategory)
                .orElseThrow(() -> new EntityNotFoundException("ServiceCategory not found: " + idCategory));
        ServiceCategoryMapper.copyToEntity(dto, existing);
        ServiceCategory saved = repository.save(existing);
        return ServiceCategoryMapper.toResponse(saved);
    }

    @Override
    public void delete(Long idCategory) {
        if (!repository.existsById(idCategory)) {
            throw new EntityNotFoundException("ServiceCategory not found: " + idCategory);
        }
        repository.deleteById(idCategory);
    }
}
