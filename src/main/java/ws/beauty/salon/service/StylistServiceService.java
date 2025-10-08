package ws.beauty.salon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import ws.beauty.salon.dto.StylistServiceDTO;
import ws.beauty.salon.model.StylistService;

import ws.beauty.salon.repository.StylistServiceRepository;

public class StylistServiceService {
    @Autowired
    private StylistServiceRepository repo;

    public List<StylistServiceDTO> getAll() {
        return repo.findAll().stream()
            .map(e -> {
                StylistServiceDTO dto = new StylistServiceDTO();
                //dto.setIdStylist(e.getId().getIdStylist());
                //dto.setIdService(e.getId().getIdService());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public StylistServiceDTO create(StylistServiceDTO dto) {
        if (dto.getIdStylist() == null || dto.getIdService() == null) {
            throw new RuntimeException("Stylist ID and Service ID are required");
        }

        StylistService ss = new StylistService();
        
        //StylistService pk = new StylistService(dto.getIdStylist(), dto.getIdService());
        //ss.setId(pk);
        
        repo.save(ss);

        return dto;
    }

    public void delete(Long stylistId, Long serviceId) {
        //StylistService pk = new StylistService(stylistId, serviceId);
        //repo.deleteById(pk);
    }
}
