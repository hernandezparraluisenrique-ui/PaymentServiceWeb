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
  
    @GetMapping("/appointments/{id}")
    AppointmentResponse getAppointmentById(@PathVariable("id") Integer appointmentId);

    @GetMapping("/appointments/client/{clientId}")
    List<AppointmentResponse> getAppointmentsByClientId(@PathVariable("clientId") Integer clientId);

    @GetMapping("/appointments/stylist/{stylistId}")
    List<AppointmentResponse> getAppointmentsByStylistId(@PathVariable("stylistId") Integer stylistId);

}
