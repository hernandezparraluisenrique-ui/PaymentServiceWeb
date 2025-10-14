package ws.beauty.salon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    @JsonProperty("id Review")
    private Integer idReview;

    // Solo el ID del cliente
    @JsonProperty("id Client")
    private Integer idClient;

    // Solo el ID del servicio
    @JsonProperty("id Service")
    private Integer idService;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("rating")
    private Integer rating;

    @JsonProperty("sentiment")
    private String sentiment;

    private String firstName;

    private String serviceName;

}
