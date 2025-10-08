package ws.beauty.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
}