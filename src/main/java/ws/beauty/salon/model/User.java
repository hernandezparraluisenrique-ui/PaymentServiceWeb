package ws.beauty.salon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Integer id;

    @Column(name = "userName", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @OneToOne
    @JoinColumn(name = "client", referencedColumnName = "idClient")
    private Client client;

    @OneToOne
    @JoinColumn(name = "stylist", referencedColumnName = "idStylist")
    private Stylist stylist;
}
