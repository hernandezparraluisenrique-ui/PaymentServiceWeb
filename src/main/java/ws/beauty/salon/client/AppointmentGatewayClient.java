package ws.beauty.salon.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ws.beauty.salon.dto.AppointmentResponse;

@FeignClient(
    name = "BEAUTY-SALON-REST",
    url = "https://beauty-salon-gateway.onrender.com"
)
public interface AppointmentGatewayClient {

    @GetMapping("/api/v1/appointments/{id}")
    AppointmentResponse getAppointmentById(@PathVariable Integer id);

    @GetMapping("/api/v1/appointments/client/{clientId}")
    List<AppointmentResponse> getAppointmentsByClientId(@PathVariable Integer clientId);

    @GetMapping("/api/v1/appointments/stylist/{stylistId}")
    List<AppointmentResponse> getAppointmentsByStylistId(@PathVariable Integer stylistId);
}