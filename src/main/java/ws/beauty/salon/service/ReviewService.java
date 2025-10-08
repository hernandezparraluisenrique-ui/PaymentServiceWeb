package ws.beauty.salon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.beauty.salon.model.Review;
import ws.beauty.salon.repository.ReviewRepository;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private ReviewRepository repository;

    // Obtener todas las reseñas
    public List<Review> getAll() {
        return repository.findAll();
    }

    // Obtener reseñas por cliente
    public List<Review> getReviewsByClient(Integer idClient) {
        return repository.findByClientId(idClient);
    }

    // Obtener reseñas por servicio
    public List<Review> getReviewsByService(Integer idService) {
        return repository.findByServiceId(idService);
    }

    // Guardar o actualizar una reseña
    public Review save(Review review) {
        return repository.save(review);
    }

    // Buscar reseña por ID
    public Optional<Review> getById(Integer idReview) {
        return repository.findById(idReview);
    }

    // Eliminar reseña por ID
    public void delete(Integer idReview) {
        repository.deleteById(idReview);
    }
}
