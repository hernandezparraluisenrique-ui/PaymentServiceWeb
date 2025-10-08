package ws.beauty.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.RescheduleRequest;

public interface RescheduleRequestRepository extends JpaRepository<RescheduleRequest, Long> {
    //Busca al client por su Id
    List<RescheduleRequest> findByClient_IdClient(Long clientId);
    
    List<RescheduleRequest> findByStatus(String status);
}
