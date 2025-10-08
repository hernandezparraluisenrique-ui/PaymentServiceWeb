package ws.beauty.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.ServiceCategory;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long>{
    
}