package client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ws.beauty.salon.dto.AppointmentResponse;

@FeignClient(
        name = "api-gateway",
        url = "${gateway.url}" // http://localhost:8080 o Render URL
)  
public interface AppointmentGatewayClient {
    @GetMapping("/core/api/v1/appointments/{id}")
    AppointmentResponse getAppointment(@PathVariable Integer id);

    @GetMapping("/core/api/v1/appointments/client/{clientId}")
    List<AppointmentResponse> getAppointmentsByClient(@PathVariable Integer clientId);

    @GetMapping("/core/api/v1/appointments/stylist/{stylistId}")
    List<AppointmentResponse> getAppointmentsByStylist(@PathVariable Integer stylistId);
    
    AppointmentResponse getAppointmentById(Integer appointmentId);

}
