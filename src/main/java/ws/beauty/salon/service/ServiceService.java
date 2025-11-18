package ws.beauty.salon.service;

import java.util.List;

import ws.beauty.salon.dto.ServiceRequest;
import ws.beauty.salon.dto.ServiceResponse;

public interface ServiceService {
   // Obtiene todos los servicios utilizando paginación (page y pageSize).
List<ServiceResponse> findAll(int page, int pageSize);

// Busca un servicio específico por su ID.
ServiceResponse findById(Integer id);

// Crea un nuevo servicio en el sistema.
ServiceResponse create(ServiceRequest req);

// Actualiza la información de un servicio existente según su ID.
ServiceResponse update(Integer id, ServiceRequest req);

// Busca servicios cuyo nombre coincida parcial o totalmente con el parámetro.
List<ServiceResponse> findByServiceName(String name);

// Obtiene todos los servicios que pertenecen a una categoría específica.
List<ServiceResponse> findByCategoryId(Integer categoryId);

}
