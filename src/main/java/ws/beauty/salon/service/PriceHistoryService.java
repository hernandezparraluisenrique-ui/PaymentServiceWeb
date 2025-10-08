package ws.beauty.salon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ws.beauty.salon.dto.PriceHistoryDTO;
import ws.beauty.salon.model.PriceHistory;
import ws.beauty.salon.repository.PriceHistoryRepository;

@Service
public class PriceHistoryService {
     @Autowired
    private PriceHistoryRepository repo;

    @Autowired
    private ModelMapper mapper;

    public List<PriceHistoryDTO> getAll() {
        return repo.findAll().stream()
            .map(entity -> {
                PriceHistoryDTO dto = mapper.map(entity, PriceHistoryDTO.class);
                //dto.setIdService(entity.getIdService());
                //dto.setChangedBy(entity.getChangedBy() != null ? entity.getChangedBy().getIdUser() : null);
                return dto;
            })
            .collect(Collectors.toList());
    }

    public PriceHistoryDTO getById(Long id) {
        PriceHistory e = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Price history not found"));
        PriceHistoryDTO dto = mapper.map(e, PriceHistoryDTO.class);
        //dto.setIdService(e.getIdService());
        //dto.setChangedBy(e.getChangedBy() != null ? e.getChangedBy().getIdUser() : null);
        return dto;
    }

    public PriceHistoryDTO create(PriceHistoryDTO dto) {
        PriceHistory ent = new PriceHistory();
        
        
        //ent.setIdService(dto.getIdService());
        
        //Se ocupa la clase de User 
       /* if (dto.getChangedBy() != null) {
            User u = userRepo.findById(dto.getChangedBy())
                .orElseThrow(() -> new RuntimeException("User not found"));
            ent.setChangedBy(u);
        }*/
        
        ent.setOldPrice(dto.getOldPrice());
        ent.setNewPrice(dto.getNewPrice());
        
        repo.save(ent);
        return getById(ent.getIdPrice());
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
