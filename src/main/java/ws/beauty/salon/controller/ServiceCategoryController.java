package ws.beauty.salon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ws.beauty.salon.dto.ServiceCategoryRequest;
import ws.beauty.salon.dto.ServiceCategoryResponse;
import ws.beauty.salon.service.ServiceCategoryService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/service-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ServiceCategoryController {

    private final ServiceCategoryService service;

    @GetMapping
    public List<ServiceCategoryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{idCategory}")
    public ServiceCategoryResponse getById(@PathVariable Long idCategory) {
        return service.findById(idCategory);
    }

    @PostMapping
    public ResponseEntity<ServiceCategoryResponse> create(@Valid @RequestBody ServiceCategoryRequest req) {
        ServiceCategoryResponse created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/v1/service-categories/" + created.getIdCategory()))
                .body(created);
    }

    @PutMapping("/{idCategory}")
    public ServiceCategoryResponse update(@PathVariable Long idCategory, @Valid @RequestBody ServiceCategoryRequest req) {
        return service.update(idCategory, req);
    }

    @DeleteMapping("/{idCategory}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idCategory) {
        service.delete(idCategory);
    }
}
