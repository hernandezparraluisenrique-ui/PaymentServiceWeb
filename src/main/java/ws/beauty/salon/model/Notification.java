package ws.beauty.salon.model;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification")
    @JsonProperty("idNotification")
    private Integer id;

    @Column(name = "id_client", nullable = false)
    @JsonProperty("clientId")
    private Integer clientId;

    @Column(name = "message", columnDefinition = "TEXT")
    @JsonProperty("message")
    private String message;

    @Column(name = "sent_via", length = 20)
    @JsonProperty("sentVia")
    private String sentVia;

    @Column(name = "sent_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonProperty("sentAt")
    private LocalDateTime sentAt;
}
