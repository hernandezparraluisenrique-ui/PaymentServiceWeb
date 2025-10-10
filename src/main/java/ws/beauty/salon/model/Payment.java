package ws.beauty.salon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_payment")
    @JsonProperty("idPayment")
    private Integer id;

    @Column(name = "amount")
    @JsonProperty("amount")
    private Double amount;

    @Column(name = "payment_date")
    @JsonProperty("paymentDate")
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "appointment_id", referencedColumnName = "id_appointment")
    @JsonProperty("appointment")
    private Appointment appointment;
}

