package ws.beauty.salon.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.beauty.salon.dto.ReviewRequestDTO;
import ws.beauty.salon.model.Review;
import ws.beauty.salon.service.ReviewService;

@RestController
@RequestMapping("reviews")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Reviews", description = "Provides methods for managing reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @Autowired
    private ModelMapper modelMapper;

    // Obtener todas las reseñas
    @Operation(summary = "Get all reviews")
    @ApiResponse(responseCode = "200", description = "Found reviews",
        content = @Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = Review.class))))
    @GetMapping
    public List<Review> getAll() {
        return service.getAll();
    }

    // Obtener reseña por ID
    @Operation(summary = "Get a review by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Review.class))),
        @ApiResponse(responseCode = "404", description = "Review not found", content = @Content)
    })
    @GetMapping("{idReview}")
    public ResponseEntity<Review> getById(@PathVariable Integer idReview) {
        Optional<Review> review = service.getById(idReview);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener reseñas por cliente
    @Operation(summary = "Get reviews by client ID")
    @GetMapping("/client/{idClient}")
    public List<Review> getByClient(@PathVariable Integer idClient) {
        return service.getReviewsByClient(idClient);
    }

    // Obtener reseñas por servicio
    @Operation(summary = "Get reviews by service ID")
    @GetMapping("/service/{idService}")
    public List<Review> getByService(@PathVariable Integer idService) {
        return service.getReviewsByService(idService);
    }

    // Crear o actualizar reseña
    @Operation(summary = "Create or update a review")
    @ApiResponse(responseCode = "201", description = "Review created",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReviewRequestDTO.class)))
    @PostMapping
    public ResponseEntity<ReviewRequestDTO> add(@RequestBody ReviewRequestDTO reviewDTO) {
        Review savedReview = service.save(convertToEntity(reviewDTO));
        ReviewRequestDTO response = convertToDTO(savedReview);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Eliminar reseña por ID
    @Operation(summary = "Delete a review by its ID")
    @ApiResponse(responseCode = "204", description = "Review deleted")
    @DeleteMapping("{idReview}")
    public ResponseEntity<Void> delete(@PathVariable Integer idReview) {
        service.delete(idReview);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Conversiones DTO <-> Entity
    private ReviewRequestDTO convertToDTO(Review review) {
        return modelMapper.map(review, ReviewRequestDTO.class);
    }

    private Review convertToEntity(ReviewRequestDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }
}
