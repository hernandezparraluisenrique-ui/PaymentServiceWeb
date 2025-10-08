package ws.beauty.salon.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ws.beauty.salon.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Buscar reseñas por cliente
    @Query("SELECT r FROM Review r WHERE r.client.id = :idClient")
    List<Review> findByClientId(@Param("idClient") Integer idClient);

    // Buscar reseñas por servicio
    @Query("SELECT r FROM Review r WHERE r.service.id = :idService")
    List<Review> findByServiceId(@Param("idService") Integer idService);
}
