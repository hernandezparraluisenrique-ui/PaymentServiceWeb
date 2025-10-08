package ws.beauty.salon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ws.beauty.salon.model.AppointmentNote;
//import ws.beauty.salon.model.Appointment;
import ws.beauty.salon.repository.AppointmentNoteRepository;
//import ws.beauty.salon.repository.AppointmentRepository;

@Service
@Transactional
public class AppointmentNoteService {

    @Autowired
    private AppointmentNoteRepository repository;

    //@Autowired
    //private AppointmentRepository appointmentRepository;

    // Obtener todas las notas
    public List<AppointmentNote> getAll() {
        return repository.findAll();
    }

    // Obtener nota por ID
    public Optional<AppointmentNote> getById(Integer idNote) {
        return repository.findById(idNote);
    }

    // Guardar nota
    public AppointmentNote save(AppointmentNote note) {
        return repository.save(note);
    }

    // Eliminar nota
    /*public void delete(Integer idNote) {
        repository.deleteById(idNote);
    }*/

    // Obtener notas por cita
    public List<AppointmentNote> getByAppointment(Integer appointmentId) {
        return repository.findByAppointmentId(appointmentId);
    }

    // Asignar Appointment a la nota antes de guardar
    /*public AppointmentNote assignAppointment(AppointmentNote note, Integer appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        note.setAppointment(appointment);
        return note;
    }*/
}
