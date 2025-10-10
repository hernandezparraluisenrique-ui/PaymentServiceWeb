package ws.beauty.salon.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import ws.beauty.salon.dto.ClientRequestDTO;
import ws.beauty.salon.model.Client;
import ws.beauty.salon.service.ClientService;

@RestController
@RequestMapping("clients")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Clients", description = "Provides methods for managing clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all clients")
    @GetMapping
    public List<Client> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get all clients with pagination")
    @GetMapping(value = "pagination", params = { "page", "pageSize" })
    public List<Client> getAllPaginated(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int pageSize) {
        return service.getAll(page, pageSize);
    }

    @Operation(summary = "Get all clients ordered by name")
    @GetMapping("orderByName")
    public List<Client> getAllOrderByName() {
        return service.getAllOrderByName();
    }

    @Operation(summary = "Get a client by ID")
    @GetMapping("{idClient}")
    public ResponseEntity<Client> getById(@PathVariable Integer idClient) {
        Client client = service.getById(idClient);
        return (client != null)
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get a client by email")
    @GetMapping("email/{email}")
    public ResponseEntity<Client> getByEmail(@PathVariable String email) {
        Client client = service.getByEmail(email);
        return (client != null)
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Search clients by name")
    @GetMapping("search/{name}")
    public List<Client> searchByName(@PathVariable String name) {
        return service.searchByName(name);
    }

    @Operation(summary = "Register a client")
    @PostMapping
    public ResponseEntity<ClientRequestDTO> add(@RequestBody ClientRequestDTO dto) {
        Client saved = service.save(convertToEntity(dto));
        return new ResponseEntity<>(convertToDTO(saved), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a client")
    @PutMapping("{idClient}")
    public ResponseEntity<ClientRequestDTO> update(@PathVariable Integer idClient,
                                                   @RequestBody ClientRequestDTO dto) {
        Client existing = service.getById(idClient);
        if (existing == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Client updated = convertToEntity(dto);
        updated.setId(idClient);
        service.save(updated);
        return new ResponseEntity<>(convertToDTO(updated), HttpStatus.OK);
    }

    @Operation(summary = "Delete a client")
    @DeleteMapping("{idClient}")
    public ResponseEntity<Void> delete(@PathVariable Integer idClient) {
        service.delete(idClient);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ClientRequestDTO convertToDTO(Client client) {
        return modelMapper.map(client, ClientRequestDTO.class);
    }

    private Client convertToEntity(ClientRequestDTO dto) {
        return modelMapper.map(dto, Client.class);
    }
}
