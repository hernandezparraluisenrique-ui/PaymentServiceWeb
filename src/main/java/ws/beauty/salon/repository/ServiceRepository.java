package ws.beauty.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.beauty.salon.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    
}
