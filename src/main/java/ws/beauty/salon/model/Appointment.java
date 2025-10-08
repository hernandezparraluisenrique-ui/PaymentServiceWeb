package ws.beauty.salon.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Appointment")
public class Appointment {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appointment")
    private Long idAppointment;

    @Column(name = "appointment_datetime", nullable = false)
    private LocalDateTime appointmentDatetime;

    @Column(name = "status", length = 20)
    private String status = "pending";

    @Column(name = "id_client", nullable = false)
    private Long idClient;

    @Column(name = "id_stylist")
    private Long idStylist;

    @Column(name = "id_service")
    private Long idService;

    @PrePersist
    void ensureDefaultStatus() {
        if (status == null || status.isBlank()) {
            status = "pending";
        }
    }
}