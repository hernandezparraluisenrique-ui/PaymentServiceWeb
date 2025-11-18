package ws.beauty.salon.Controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import ws.beauty.salon.controller.PaymentController;
import ws.beauty.salon.dto.PaymentRequest;
import ws.beauty.salon.dto.PaymentResponse;
import ws.beauty.salon.service.PaymentService;

@WebMvcTest(controllers = PaymentController.class)
public class PaymentControllerTest {
   @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    PaymentService service;

     private static final String BASE = "/api/v1/payments";

@BeforeEach
    void setup() {
        reset(service);
    }

    // =============================
    // Helpers DTO
    // =============================

    /**
     * Helper method to create PaymentResponse for testing
     */
    private PaymentResponse resp(Integer id, Integer appointmentId, Double amount, LocalDateTime date) {
        return PaymentResponse.builder()
                .id(id)
                .appointmentId(appointmentId)
                .amount(amount)
                .paymentDate(date)
                .build();
    }

    /**
     * Helper method to create PaymentRequest for testing
     */
    private PaymentRequest req(Double amount, LocalDateTime date, Integer appointmentId) {
        return PaymentRequest.builder()
                .amount(amount)
                .paymentDate(date)
                .appointmentId(appointmentId)
                .build();
    }

    // =============================
    // GET /api/v1/payments?page=&pageSize= (paginado)
    // =============================

    @ParameterizedTest(name = "GET /api/v1/payments?page={0}&pageSize={1} → 200")
    @CsvSource({"0,5", "1,10", "0,10"})
    void findAll_paginated_ok(int page, int size) throws Exception {
        when(service.findAll(page, size)).thenReturn(List.of(
                resp(1, 101, 500.0, LocalDateTime.now()),
                resp(2, 102, 300.0, LocalDateTime.now())
        ));

        mvc.perform(get(BASE)
                .queryParam("page", String.valueOf(page))
                .queryParam("pageSize", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].amount").value(500.0))
                .andExpect(jsonPath("$[1].amount").value(300.0));
    }

    @Test
    @DisplayName("GET /api/v1/payments?page=-1&pageSize=10 → 400 (page negativo)")
    void findAll_paginated_negativePageThrowsException() throws Exception {
        mvc.perform(get(BASE)
                .queryParam("page", "-1")
                .queryParam("pageSize", "10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/payments?page=0&pageSize=-1 → 400 (pageSize negativo)")
    void findAll_paginated_negativePageSizeThrowsException() throws Exception {
        mvc.perform(get(BASE)
                .queryParam("page", "0")
                .queryParam("pageSize", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/payments?page=0&pageSize=0 → 400 (ambos cero)")
    void findAll_paginated_bothZeroThrowsException() throws Exception {
        mvc.perform(get(BASE)
                .queryParam("page", "0")
                .queryParam("pageSize", "0"))
                .andExpect(status().isBadRequest());
    }

    // =============================
    // GET /{idPayment}
    // =============================

    @Test
    @DisplayName("GET /{idPayment} existente → 200")
    void findById_ok() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(service.findById(1)).thenReturn(
                resp(1, 101, 150.0, now)
        );

        mvc.perform(get(BASE + "/{idPayment}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.appointmentId").value(101))
                .andExpect(jsonPath("$.amount").value(150.0));
    }

    // =============================
    // POST /api/v1/payments
    // =============================

    @Test
    @DisplayName("POST válido → 201 creado")
    void create_ok() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        
        PaymentRequest rq = req(500.0, now, 101);
        PaymentResponse created = resp(1, 101, 500.0, now);

        when(service.create(any(PaymentRequest.class))).thenReturn(created);

        mvc.perform(post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/payments/1"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(500.0))
                .andExpect(jsonPath("$.appointmentId").value(101));
    }

    @Test
    @DisplayName("POST inválido → 400")
    void create_invalid() throws Exception {
        mvc.perform(post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }


    // =============================
    // GET /appointment/{appointmentId}
    // =============================

    @Test
    @DisplayName("GET /appointment/{appointmentId} → 200")
    void findByAppointmentId_ok() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(service.findByAppointmentId(101)).thenReturn(
                resp(1, 101, 500.0, now)
        );

        mvc.perform(get(BASE + "/appointment/{appointmentId}", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.appointmentId").value(101))
                .andExpect(jsonPath("$.amount").value(500.0));
    }

    // =============================
    // GET /client/{clientId}?page=&pageSize=
    // =============================

    @Test
    @DisplayName("GET /client/{clientId} con paginación → 200")
    void findByClientId_ok() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(service.findByClientId(201, 0, 10)).thenReturn(List.of(
                resp(1, 101, 500.0, now),
                resp(2, 102, 300.0, now)
        ));

        mvc.perform(get(BASE + "/client/{clientId}", 201)
                .queryParam("page", "0")
                .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].amount").value(500.0));
    }

    @Test
    @DisplayName("GET /client/{clientId}?page=-1 → 400")
    void findByClientId_negativePageThrowsException() throws Exception {
        mvc.perform(get(BASE + "/client/{clientId}", 201)
                .queryParam("page", "-1")
                .queryParam("pageSize", "10"))
                .andExpect(status().isBadRequest());
    }

    // =============================
    // GET /stylist/{stylistId}?page=&pageSize=
    // =============================

    @Test
    @DisplayName("GET /stylist/{stylistId} con paginación → 200")
    void findByStylistId_ok() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        when(service.findByStylistId(301, 0, 10)).thenReturn(List.of(
                resp(1, 101, 500.0, now)
        ));

        mvc.perform(get(BASE + "/stylist/{stylistId}", 301)
                .queryParam("page", "0")
                .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount").value(500.0));
    }

    @Test
    @DisplayName("GET /stylist/{stylistId}?pageSize=-1 → 400")
    void findByStylistId_negativePageSizeThrowsException() throws Exception {
        mvc.perform(get(BASE + "/stylist/{stylistId}", 301)
                .queryParam("page", "0")
                .queryParam("pageSize", "-1"))
                .andExpect(status().isBadRequest());
    }

    // =============================
    // GET /dates?start=&end=&page=&pageSize=
    // =============================

    @Test
    @DisplayName("GET /dates con rango válido → 200")
    void findByPaymentDateBetween_ok() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);
        
        when(service.findByPaymentDateBetween(start, end, 0, 10)).thenReturn(List.of(
                resp(1, 101, 500.0, LocalDateTime.of(2024, 6, 15, 10, 0))
        ));

        mvc.perform(get(BASE + "/dates")
                .queryParam("start", start.toString())
                .queryParam("end", end.toString())
                .queryParam("page", "0")
                .queryParam("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount").value(500.0));
    }

    @Test
    @DisplayName("GET /dates?page=0&pageSize=0 → 400")
    void findByPaymentDateBetween_bothZeroThrowsException() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);

        mvc.perform(get(BASE + "/dates")
                .queryParam("start", start.toString())
                .queryParam("end", end.toString())
                .queryParam("page", "0")
                .queryParam("pageSize", "0"))
                .andExpect(status().isBadRequest());
    }

    // =============================
    // GET /total/dates?start=&end=
    // =============================

    @Test
    @DisplayName("GET /total/dates con rango válido → 200")
    void getTotalAmountBetweenDates_ok() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59);
        
        when(service.getTotalAmountBetweenDates(start, end)).thenReturn(1500.0);

        mvc.perform(get(BASE + "/total/dates")
                .queryParam("start", start.toString())
                .queryParam("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("1500.0"));
    }

    // =============================
    // GET /total/client/{clientId}
    // =============================

    @Test
    @DisplayName("GET /total/client/{clientId} → 200")
    void getTotalAmountByClient_ok() throws Exception {
        when(service.getTotalAmountByClient(201)).thenReturn(2500.0);

        mvc.perform(get(BASE + "/total/client/{clientId}", 201))
                .andExpect(status().isOk())
                .andExpect(content().string("2500.0"));
    }

    // =============================
    // GET /total/stylist/{stylistId}
    // =============================

    @Test
    @DisplayName("GET /total/stylist/{stylistId} → 200")
    void getTotalAmountByStylist_ok() throws Exception {
        when(service.getTotalAmountByStylist(301)).thenReturn(3500.0);

        mvc.perform(get(BASE + "/total/stylist/{stylistId}", 301))
                .andExpect(status().isOk())
                .andExpect(content().string("3500.0"));
    }

    // =============================
    // GET /exists/{appointmentId}
    // =============================

    @Test
    @DisplayName("GET /exists/{appointmentId} → true")
    void existsByAppointmentId_true() throws Exception {
        when(service.existsByAppointmentId(101)).thenReturn(true);

        mvc.perform(get(BASE + "/exists/{appointmentId}", 101))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("GET /exists/{appointmentId} → false")
    void existsByAppointmentId_false() throws Exception {
        when(service.existsByAppointmentId(999)).thenReturn(false);

        mvc.perform(get(BASE + "/exists/{appointmentId}", 999))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}
