package ws.beauty.salon.mapper;

import ws.beauty.salon.dto.AppointmentRequest;
import ws.beauty.salon.dto.AppointmentResponse;
import ws.beauty.salon.model.Appointment;

public final class AppointmentMapper {

    private AppointmentMapper() {}

    public static AppointmentResponse toResponse(Appointment appointment) {
        if (appointment == null) return null;

        return AppointmentResponse.builder()
                .idAppointment(appointment.getIdAppointment())
                .appointmentDatetime(appointment.getAppointmentDatetime())
                .status(appointment.getStatus())
                .idClient(appointment.getIdClient())     // <-- usar IDs directos
                .idStylist(appointment.getIdStylist())
                .idService(appointment.getIdService())
                .build();
    }

    public static Appointment toEntity(AppointmentRequest dto) {
        if (dto == null) return null;

        return Appointment.builder()
                .appointmentDatetime(dto.getAppointmentDatetime())
                .status(dto.getStatus())
                .idClient(dto.getIdClient())             // <-- setear IDs
                .idStylist(dto.getIdStylist())
                .idService(dto.getIdService())
                .build();
    }

    public static void copyToEntity(AppointmentRequest dto, Appointment entity) {
        if (dto == null || entity == null) return;

        entity.setAppointmentDatetime(dto.getAppointmentDatetime());
        entity.setStatus(dto.getStatus());
        entity.setIdClient(dto.getIdClient());           // <-- actualizar IDs
        entity.setIdStylist(dto.getIdStylist());
        entity.setIdService(dto.getIdService());
    }
}
