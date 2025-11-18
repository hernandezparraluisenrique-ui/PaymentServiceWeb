package ws.beauty.salon.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ws.beauty.salon.dto.LoginUserDto;
import ws.beauty.salon.dto.RegisterUserDto;
import ws.beauty.salon.model.Client;
import ws.beauty.salon.model.Stylist;
import ws.beauty.salon.model.User;
import ws.beauty.salon.repository.ClientRepository;
import ws.beauty.salon.repository.StylistRepository;
import ws.beauty.salon.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final StylistRepository stylistRepository;
    
    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(input.getRole());
        
        // Prevent registering both client and stylist at the same time
        if (input.getClientId() != null && input.getStylistId() != null) {
            throw new IllegalArgumentException("A user cannot be linked to both a client and a stylist.");
        }
        
        // Relation with Client
        if (input.getClientId() != null) {
            Client client = clientRepository.findById(input.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found: " + input.getClientId()));
            user.setClient(client);
        }
        
        // Relation with Stylist
        if (input.getStylistId() != null) {
            Stylist stylist = stylistRepository.findById(input.getStylistId())
                    .orElseThrow(() -> new EntityNotFoundException("Stylist not found: " + input.getStylistId()));
            user.setStylist(stylist);
        }
        
        return userRepository.save(user);
    }
    
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}