package ws.beauty.salon.service;

import java.util.List;
import org.springframework.stereotype.Service;
import ws.beauty.salon.dto.ClientRequest;
import ws.beauty.salon.dto.ClientResponse;
import ws.beauty.salon.mapper.ClientMapper;
import ws.beauty.salon.model.Client;
import ws.beauty.salon.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public List<ClientResponse> findAll() {
        return repository.findAll().stream()
                .map(ClientMapper::toResponse)
                .toList();
    }

    @Override
    public ClientResponse findById(Long idClient) {
        Client client = repository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + idClient));
        return ClientMapper.toResponse(client);
    }

    @Override
    public ClientResponse create(ClientRequest request) {
        Client saved = repository.save(ClientMapper.toEntity(request));
        return ClientMapper.toResponse(saved);
    }

    @Override
    public ClientResponse update(Long idClient, ClientRequest dto) {
        Client existing = repository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + idClient));
        ClientMapper.copyToEntity(dto, existing);
        Client saved = repository.save(existing);
        return ClientMapper.toResponse(saved);
    }

    @Override
    public void delete(Long idClient) {
        if (!repository.existsById(idClient)) {
            throw new EntityNotFoundException("Client not found: " + idClient);
        }
        repository.deleteById(idClient);
    }
}
