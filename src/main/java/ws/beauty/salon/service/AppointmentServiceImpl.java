package ws.beauty.salon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ws.beauty.salon.dto.AppointmentRequest;
import ws.beauty.salon.dto.AppointmentResponse;
import ws.beauty.salon.mapper.AppointmentMapper;
import ws.beauty.salon.model.Appointment;
import ws.beauty.salon.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Override
    public List<AppointmentResponse> findAll() {
        return repository.findAll().stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    @Override
    public AppointmentResponse findById(Long idAppointment) {
        Appointment appointment = repository.findById(idAppointment)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + idAppointment));
        return AppointmentMapper.toResponse(appointment);
    }

    @Override
    public AppointmentResponse create(AppointmentRequest request) {
        Appointment saved = repository.save(AppointmentMapper.toEntity(request));
        return AppointmentMapper.toResponse(saved);
    }

    @Override
    public AppointmentResponse update(Long idAppointment, AppointmentRequest dto) {
        Appointment existing = repository.findById(idAppointment)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found: " + idAppointment));
        AppointmentMapper.copyToEntity(dto, existing);
        Appointment saved = repository.save(existing);
        return AppointmentMapper.toResponse(saved);
    }

    @Override
    public void delete(Long idAppointment) {
        if (!repository.existsById(idAppointment)) {
            throw new EntityNotFoundException("Appointment not found: " + idAppointment);
        }
        repository.deleteById(idAppointment);
    }
}
