package ws.beauty.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.RescheduleRequest;

public interface RescheduleRequestRepository extends JpaRepository<RescheduleRequest, Integer> {
    //Busca al client por su Id
    List<RescheduleRequest> findByClient_Id(Integer Id);
    
    List<RescheduleRequest> findByStatus(String status);
}
