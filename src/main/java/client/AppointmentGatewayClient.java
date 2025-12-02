package client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ws.beauty.salon.dto.AppointmentResponse;

@FeignClient(name = "api-gateway")   
public interface AppointmentGatewayClient {
    @GetMapping("/api/v1/core/appointments/{id}")
    AppointmentResponse getAppointmentById(@PathVariable("id") Integer id);
}
