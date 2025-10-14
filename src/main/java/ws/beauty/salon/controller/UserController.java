package ws.beauty.salon.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import ws.beauty.salon.dto.UserRequest;
import ws.beauty.salon.dto.UserResponse;
import ws.beauty.salon.service.UserService;
import ws.beauty.salon.model.User;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Provides methods for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "Found user", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) })
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll();
    }

    @Operation(summary = "Get all users with pagination")
    @ApiResponse(responseCode = "200", description = "Found fabrics", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class))) })
    @GetMapping(value = "pagination", params = { "page", "pageSize" })
    public List<UserResponse> getAllPaginated(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return userService.getAll(page, pageSize);
    }

    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fabric found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid fabric id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Fabric not found", content = @Content) })
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "Registered user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
    })
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {
        UserResponse savedUser = userService.create(userRequest);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @RequestBody UserRequest userRequest) {
        return userService.update(id, userRequest);
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    // ------------------- CONSULTAS ESPECIALIZADAS -------------------

    @Operation(summary = "Get users by role")
    @GetMapping("/role/{role}")
    public List<UserResponse> getByRole(@PathVariable String role) {
        return userService.findByRole(role);
    }

    @Operation(summary = "Get users by username")
    @GetMapping("/search/{username}")
    public List<UserResponse> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    public User convertToEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }
}
