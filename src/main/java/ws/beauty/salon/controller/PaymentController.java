package ws.beauty.salon.controller;

import java.util.List;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import ws.beauty.salon.dto.PaymentRequestDTO;
import ws.beauty.salon.model.Payment;
import ws.beauty.salon.service.PaymentService;

@RestController
@RequestMapping("payments")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Payments", description = "Provides methods for managing payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    /*@Autowired
    private ModelMapper modelMapper;*/

    @Operation(summary = "Get all payments")
    @ApiResponse(responseCode = "200", description = "Found payments", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Payment.class))) })
    @GetMapping
    public List<Payment> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get a payment by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Payment.class)) }),
            @ApiResponse(responseCode = "404", description = "Payment not found", content = @Content) })
    @GetMapping("{id}")
    public ResponseEntity<Payment> getById(@PathVariable Integer id) {
        Payment payment = service.getById(id);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @Operation(summary = "Register a payment")
    @ApiResponse(responseCode = "201", description = "Registered payment", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentRequestDTO.class)) })
    @PostMapping
    public ResponseEntity<PaymentRequestDTO> add(@RequestBody PaymentRequestDTO paymentDTO) {
        Payment payment = service.convertToEntity(paymentDTO);
        Payment savedPayment = service.save(payment);
        PaymentRequestDTO savedDTO = service.convertToDTO(savedPayment);
        return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a payment by its id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (service.getById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*private PaymentRequestDTO convertToDTO(Payment payment) {
        return modelMapper.map(payment, PaymentRequestDTO.class);
    }

    private Payment convertToEntity(PaymentRequestDTO dto) {
        return modelMapper.map(dto, Payment.class);
    }*/
}

