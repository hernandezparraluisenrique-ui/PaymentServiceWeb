package ws.beauty.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ws.beauty.salon.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
    
}