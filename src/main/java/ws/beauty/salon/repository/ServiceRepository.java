package ws.beauty.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ws.beauty.salon.model.Service1;

public interface ServiceRepository extends JpaRepository<Service1, Integer> {
    
}
