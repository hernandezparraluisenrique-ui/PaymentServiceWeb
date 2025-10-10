package ws.beauty.salon.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ws.beauty.salon.model.Payment;
import ws.beauty.salon.model.Appointment;
import ws.beauty.salon.repository.PaymentRepository;
import ws.beauty.salon.repository.AppointmentRepository;

import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public boolean existsByAppointmentId(Integer appointmentId) {
        return repository.existsByAppointmentId(appointmentId);
    }

    public Payment convertToEntity(ws.beauty.salon.dto.PaymentRequestDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        if(dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId()).orElse(null);
            payment.setAppointment(appointment);
        }
        return payment;
    }

    public ws.beauty.salon.dto.PaymentRequestDTO convertToDTO(Payment payment) {
        ws.beauty.salon.dto.PaymentRequestDTO dto = modelMapper.map(payment, ws.beauty.salon.dto.PaymentRequestDTO.class);
        if(payment.getAppointment() != null) {
            dto.setAppointmentId(payment.getAppointment().getId());
        }
        return dto;
    }
}



