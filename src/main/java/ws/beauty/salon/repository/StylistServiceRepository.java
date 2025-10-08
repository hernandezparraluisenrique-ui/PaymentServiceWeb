package ws.beauty.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.StylistService;


public interface StylistServiceRepository extends JpaRepository<StylistService, StylistService> {
    //Busca Stylist por su Id
    List<StylistService> findByStylist_IdStylist(Long stylistId);
     //Busca el Service por su Id
    List<StylistService> findByService_IdService(Long serviceId);
}
