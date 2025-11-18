package ws.beauty.salon.model;


import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Column(name = "userName", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @OneToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    private Client client;

    @OneToOne
    @JoinColumn(name = "id_stylist", referencedColumnName = "id_stylist")
    private Stylist stylist;


      
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
@PreUpdate
private void validateExclusiveRelations() {
    // No puede ser ambos al mismo tiempo
    if (client != null && stylist != null) {
        throw new IllegalStateException("A user cannot be both a client and a stylist.");
    }
    
    // Si es CLIENT, debe tener perfil de cliente
    if ("CLIENT".equals(role) && client == null) {
        throw new IllegalStateException("A user with CLIENT role must have a client profile.");
    }
    
    // Si es STYLIST, debe tener perfil de estilista
    if ("STYLIST".equals(role) && stylist == null) {
        throw new IllegalStateException("A user with STYLIST role must have a stylist profile.");
    }
}
 
}
