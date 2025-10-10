package ws.beauty.salon.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    @JsonProperty("id_client")
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    @JsonProperty("email")
    private String email;

    @Column(name = "phone", length = 20)
    @JsonProperty("phone")
    private String phone;

    @Column(name = "registration_date")
    @JsonProperty("registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "preferences")
    @JsonProperty("preferences")
    private String preferences;

    @Column(name = "satisfaction_history")
    @JsonProperty("satisfaction_history")
    private String satisfactionHistory;
}

