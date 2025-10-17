package ws.beauty.salon.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ws.beauty.salon.dto.RescheduleRequestDTO;
import ws.beauty.salon.model.RescheduleRequest;
import ws.beauty.salon.repository.RescheduleRequestRepository;

//import ch.qos.logback.core.net.server.Client;

@Service
public class RescheduleRequestService {
    @Autowired
    private RescheduleRequestRepository repo;

   /* @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private ClientRepository clientRepo;
    */
    @Autowired
    private ModelMapper mapper;

    public List<RescheduleRequestDTO> getAll() {
        return repo.findAll().stream()
                .map(e -> {
                    RescheduleRequestDTO dto = mapper.map(e, RescheduleRequestDTO.class);
                    //metodo que ocupa la clase Appointmen ...El ID
                    //dto.setAppointmentId(e.getAppointment() != null ? (Long) e.getAppointment().getAppointment() : null);
                    //dto.setIdClient(e.getClient() != null ? ((RescheduleRequestDTO) e.getClient()).getIdClient() : null);
                    return dto;
                }).collect(Collectors.toList());
    }

    public RescheduleRequestDTO getById(Integer id) {
        RescheduleRequest e = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        RescheduleRequestDTO dto = mapper.map(e, RescheduleRequestDTO.class);
        //dto.setAppointmentId(e.getAppointment() != null ? e.getAppointment().getAppointment() : null);
       // dto.setIdClient(e.getClient() != null ? ((RescheduleRequestDTO) e.getClient()).getIdClient() : null);
        return dto;
    }

    public RescheduleRequestDTO create(RescheduleRequestDTO dto) {
        RescheduleRequest ent = new RescheduleRequest();
        if (dto.getAppointmentId() != null) {
            //RescheduleRequest a = appointmentRepo.findById(dto.getAppointmentId()).orElseThrow(() -> new RuntimeException("Appointment not found"));
            //ent.setAppointment(a);
        }
        if (dto.getIdClient() != null) {
            //Client c = (Client) clientRepo.findById(dto.getIdClient()).orElseThrow(() -> new RuntimeException("Client not found"));
            //ent.setClient(c);
        }
        ent.setRequestedDate(dto.getRequestedDate());
        ent.setReason(dto.getReason());
        ent.setStatus(dto.getStatus()!=null?dto.getStatus():"pending");
        repo.save(ent);
        return getById(ent.getIdRequest());
    }

    public RescheduleRequestDTO updateStatus(Integer id, String status) {
        RescheduleRequest e = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        e.setStatus(status);
        repo.save(e);
        return getById(id);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}
