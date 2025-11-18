package ws.beauty.salon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import ws.beauty.salon.dto.UserRequest;
import ws.beauty.salon.dto.UserResponse;
import ws.beauty.salon.mapper.UserMapper;
import ws.beauty.salon.model.Client;
import ws.beauty.salon.model.Stylist;
import ws.beauty.salon.model.User;
import ws.beauty.salon.repository.ClientRepository;
import ws.beauty.salon.repository.StylistRepository;
import ws.beauty.salon.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StylistRepository stylistRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> findAll() {
        return repository.findAll().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<User> users = repository.findAll(pageReq);
        return users.getContent().stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse findById(Integer id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse create(UserRequest dto) {
        User user = UserMapper.toEntity(dto);

        // 🔐 Encriptar contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 🔥 Prefijar el rol con ROLE_
       user.setRole(dto.getRole().toUpperCase());

        // Relación con Client
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found: " + dto.getClientId()));
            user.setClient(client);
        }

        // Relación con Stylist
        if (dto.getStylistId() != null) {
            Stylist stylist = stylistRepository.findById(dto.getStylistId())
                    .orElseThrow(() -> new EntityNotFoundException("Stylist not found: " + dto.getStylistId()));
            user.setStylist(stylist);
        }

        User saved = repository.save(user);
        return UserMapper.toResponse(saved);
    }

    @Override
    public UserResponse update(Integer id, UserRequest dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));

        // Actualiza campos básicos
        UserMapper.copyToEntity(dto, existing);

        // 🔥 Actualizar rol correctamente
        existing.setRole(dto.getRole().toUpperCase());

        // 🔐 Actualizar contraseña codificada
        existing.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Actualiza relaciones
        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new EntityNotFoundException("Client not found: " + dto.getClientId()));
            existing.setClient(client);
        }

        if (dto.getStylistId() != null) {
            Stylist stylist = stylistRepository.findById(dto.getStylistId())
                    .orElseThrow(() -> new EntityNotFoundException("Stylist not found: " + dto.getStylistId()));
            existing.setStylist(stylist);
        }

        User saved = repository.save(existing);
        return UserMapper.toResponse(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<UserResponse> findByRole(String role) {
       return repository.findByRole(role.toUpperCase()).stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(List::of)
                .orElse(List.of())
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }
}
