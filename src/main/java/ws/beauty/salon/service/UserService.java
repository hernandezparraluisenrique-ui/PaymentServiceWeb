package ws.beauty.salon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.beauty.salon.model.User;
import ws.beauty.salon.repository.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository repository;

    // Obtener todos los usuarios
    /*public List<User> getAll() {
        return repository.findAll();
    }*/

    // Obtener usuarios paginados
    public List<User> getAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<User> users = repository.findAll(pageReq);
        return users.getContent();
    }

    // Obtener todos los usuarios ordenados por nombre de usuario
    public List<User> getAllOrderByUsername() {
        return repository.findAll(Sort.by("username"));
    }

    // Guardar (crear o actualizar) usuario
    public User save(User user) {
        return repository.save(user);
    }

    // Buscar usuario por ID
    public User getById(Integer idUser) {
        Optional<User> user = repository.findById(idUser);
        return user.orElse(null);
    }

    // Eliminar usuario por ID
    /*public void delete(Integer idUser) {
        repository.deleteById(idUser);
    }*/

    // Buscar por nombre de usuario (para login)
    public Optional<User> getByUsername(String username) {
        return repository.findByUsername(username);
    }

}
