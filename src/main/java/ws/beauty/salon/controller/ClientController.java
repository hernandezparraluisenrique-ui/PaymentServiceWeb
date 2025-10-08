package ws.beauty.salon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ws.beauty.salon.dto.ClientRequest;
import ws.beauty.salon.dto.ClientResponse;
import ws.beauty.salon.service.ClientService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ClientController {

    private final ClientService service;

    @GetMapping
    public List<ClientResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{idClient}")
    public ClientResponse getById(@PathVariable Long idClient) {
        return service.findById(idClient);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest req) {
        ClientResponse created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/clients/" + created.getIdClient()))
                .body(created);
    }

    @PutMapping("/{idClient}")
    public ClientResponse update(@PathVariable Long idClient, @Valid @RequestBody ClientRequest req) {
        return service.update(idClient, req);
    }

    @DeleteMapping("/{idClient}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idClient) {
        service.delete(idClient);
    }
}
