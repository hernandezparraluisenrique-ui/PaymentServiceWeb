package ws.beauty.salon.service;

import java.util.List;

import ws.beauty.salon.dto.ClientRequest;
import ws.beauty.salon.dto.ClientResponse;

public interface ClientService {
    List<ClientResponse> findAll();

    ClientResponse findById(Long idClient);

    ClientResponse create(ClientRequest req);

    ClientResponse update(Long idClient, ClientRequest req);

    void delete(Long idClient);
}
