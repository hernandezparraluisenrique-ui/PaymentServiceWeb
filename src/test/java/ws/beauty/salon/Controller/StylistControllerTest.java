package ws.beauty.salon.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ws.beauty.salon.dto.StylistRequest;
import ws.beauty.salon.dto.StylistResponse;
import ws.beauty.salon.service.StylistService;
import ws.beauty.salon.controller.StylistController;


import java.util.Collections;
import java.util.List;

//import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StylistController.class)
class StylistControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    StylistService service;

    private static final String BASE = "/api/stylists";

    @BeforeEach
    void setup() {
        reset(service);
    }

    // =============================
    // Configuración de beans de prueba
    // =============================
    @TestConfiguration
    static class TestConfig {
        @Bean
        StylistService stylistService() {
            return mock(StylistService.class);
        }
    }

    // =============================
    // Helpers DTO
    // =============================

    private StylistResponse resp(Integer id, String first, String last, String spec, boolean available) {
        return StylistResponse.builder()
                .id(id)
                .firstName(first)
                .lastName(last)
                .specialty(spec)
                .available(available)
                .build();
    }

    private StylistRequest req(String first, String last, String spec, boolean available) {
        return StylistRequest.builder()
                .firstName(first)
                .lastName(last)
                .specialty(spec)
                .available(available)
                .build();
    }

    // =============================
    // GET /api/stylists
    // =============================

    @Test
    @DisplayName("GET /api/stylists → 200 con lista")
    void findAll_ok() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                resp(1, "Ana", "López", "Colorimetría", true),
                resp(2, "Luis", "Pérez", "Corte", false)
        ));

        mvc.perform(get(BASE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Ana"))
                .andExpect(jsonPath("$[1].specialty").value("Corte"));
    }

    @Test
    @DisplayName("GET /api/stylists → 200 con lista vacía")
    void findAll_empty() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mvc.perform(get(BASE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // =============================
    // GET paginado /pagination?page=&pageSize=
    // =============================

    @ParameterizedTest(name = "GET /pagination?page={0}&pageSize={1} → 200")
    @CsvSource({"0,5", "1,10", "2,3"})
    @DisplayName("GET paginado válido → 200")
    void pagination_ok(int page, int size) throws Exception {
        when(service.findAllPaginated(page, size))
                .thenReturn(List.of(resp(10, "María", "Díaz", "Maquillaje", true)));

        mvc.perform(get(BASE + "/pagination")
                .queryParam("page", String.valueOf(page))
                .queryParam("pageSize", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].specialty").value("Maquillaje"));
    }

    // =============================
    // GET /{id}
    // =============================

    @Test
    @DisplayName("GET /{id} existente → 200")
    void findById_ok() throws Exception {
        when(service.findById(5)).thenReturn(resp(5, "Paola", "Reyes", "Corte", true));

        mvc.perform(get(BASE + "/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Paola"))
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    @DisplayName("GET /{id} no existente → 404")
    void findById_notFound() throws Exception {
        when(service.findById(999)).thenThrow(new EntityNotFoundException("Stylist not found"));

        mvc.perform(get(BASE + "/{id}", 999))
                .andExpect(status().isNotFound());
    }

    // =============================
    // POST /api/stylists
    // =============================

    @Test
    @DisplayName("POST válido → 201 + body creado")
    void create_ok() throws Exception {
        StylistRequest rq = req("Luz", "Campos", "Uñas", true);
        StylistResponse created = resp(7, "Luz", "Campos", "Uñas", true);
        when(service.create(any(StylistRequest.class))).thenReturn(created);

        mvc.perform(post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rq)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idStylist").value(7))
                .andExpect(jsonPath("$.firstName").value("Luz"));
    }

    @Test
    @DisplayName("POST inválido (sin campos requeridos) → 400")
    void create_invalid() throws Exception {
        mvc.perform(post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // =============================
    // PUT /api/stylists/{id}
    // =============================

    @Test
    @DisplayName("PUT válido → 200 actualizado")
    void update_ok() throws Exception {
        StylistRequest rq = req("Mario", "Ruiz", "Colorista", true);
        StylistResponse updated = resp(8, "Mario", "Ruiz", "Colorista", true);
        when(service.update(eq(8), any(StylistRequest.class))).thenReturn(updated);

        mvc.perform(put(BASE + "/{id}", 8)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(rq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mario"))
                .andExpect(jsonPath("$.specialty").value("Colorista"));
    }

    @Test
    @DisplayName("PUT no existente → 404")
    void update_notFound() throws Exception {
        when(service.update(eq(999), any(StylistRequest.class)))
                .thenThrow(new EntityNotFoundException("Stylist not found"));

        mvc.perform(put(BASE + "/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req("X", "Y", "Corte", true))))
                .andExpect(status().isNotFound());
    }

    // =============================
    // DELETE /api/stylists/{id}
    // =============================

    @Test
    @DisplayName("DELETE existente → 204")
    void delete_ok() throws Exception {
        doNothing().when(service).delete(3);

        mvc.perform(delete(BASE + "/{id}", 3))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    @DisplayName("DELETE no existente → 404")
    void delete_notFound() throws Exception {
        doThrow(new EntityNotFoundException("Stylist not found")).when(service).delete(999);

        mvc.perform(delete(BASE + "/{id}", 999))
                .andExpect(status().isNotFound());
    }

    // =============================
    // GET /specialty/{specialty}
    // =============================

    @Test
    @DisplayName("GET /specialty/{specialty} → 200 con lista")
    void getBySpecialty_ok() throws Exception {
        when(service.getBySpecialty("Corte"))
                .thenReturn(List.of(resp(1, "Ana", "López", "Corte", true)));

        mvc.perform(get(BASE + "/specialty/{specialty}", "Corte"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specialty").value("Corte"));
    }

    @Test
    @DisplayName("GET /specialty/{specialty} → 200 lista vacía")
    void getBySpecialty_empty() throws Exception {
        when(service.getBySpecialty("Nada")).thenReturn(Collections.emptyList());

        mvc.perform(get(BASE + "/specialty/{specialty}", "Nada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // =============================
    // GET /available
    // =============================

    @Test
    @DisplayName("GET /available → 200 con lista de disponibles")
    void getAvailable_ok() throws Exception {
        when(service.getAvailableStylists())
                .thenReturn(List.of(resp(5, "Pablo", "Cruz", "Corte", true)));

        mvc.perform(get(BASE + "/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].available").value(true));
    }

    @Test
    @DisplayName("GET /available → 200 lista vacía")
    void getAvailable_empty() throws Exception {
        when(service.getAvailableStylists()).thenReturn(Collections.emptyList());

        mvc.perform(get(BASE + "/available"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
