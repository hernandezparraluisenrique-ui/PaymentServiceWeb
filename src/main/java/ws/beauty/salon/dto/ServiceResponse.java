package ws.beauty.salon.dto;

import java.time.Duration;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceResponse {
     private Integer id;

    @JsonProperty("service name")
    private String serviceName;

    private String description;

    private Double price;

    @JsonProperty("estimated duration")
    private Duration estimatedDuration;

    @JsonProperty("category name")
    private String categoryName;

    @JsonProperty("created at")
    private LocalDateTime createdAt;
}
