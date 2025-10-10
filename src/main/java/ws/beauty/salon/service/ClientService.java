package ws.beauty.salon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import jakarta.transaction.Transactional;
import ws.beauty.salon.repository.ClientRepository;
import ws.beauty.salon.model.Client;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public List<Client> getAll() {
        return repository.findAll();
    }

    public List<Client> getAll(int page, int pageSize) {
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Client> clients = repository.findAll(pageReq);
        return clients.getContent();
    }

    public List<Client> getAllOrderByName() {
        return repository.findAll(Sort.by("lastName").and(Sort.by("firstName")));
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public Client getById(Integer idClient) {
        return repository.findById(idClient).orElse(null);
    }

    public void delete(Integer idClient) {
        repository.deleteById(idClient);
    }

    public List<Client> searchByName(String name) {
        return repository.searchByName(name);
    }

    public Client getByEmail(String email) {
        return repository.findByEmail(email);
    }
}

