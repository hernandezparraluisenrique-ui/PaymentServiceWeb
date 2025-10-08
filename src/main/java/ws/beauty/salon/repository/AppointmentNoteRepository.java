package ws.beauty.salon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ws.beauty.salon.model.AppointmentNote;

@Repository
public interface AppointmentNoteRepository extends JpaRepository<AppointmentNote, Integer> {

    // Obtener notas por cita
    List<AppointmentNote> findByAppointmentId(Integer appointmentId);
}
