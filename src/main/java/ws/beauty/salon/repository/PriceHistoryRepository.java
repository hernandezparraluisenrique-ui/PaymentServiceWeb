package ws.beauty.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.PriceHistory;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer>{
    //Busca el service po si Id
    List<PriceHistory> findByService_Id(Integer id);
}
