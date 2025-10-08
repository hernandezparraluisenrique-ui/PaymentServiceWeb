package ws.beauty.salon.controller;

import java.util.List;
import java.util.Optional;

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
import ws.beauty.salon.dto.UserRequestDTO;
import ws.beauty.salon.model.User;
import ws.beauty.salon.service.UserService;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Users", description = "Provides methods for managing users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    //  Obtener todos los usuarios con paginación
    @Operation(summary = "Get all users with pagination")
    @GetMapping(value = "pagination", params = { "page", "pageSize" })
    public List<User> getAllPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return service.getAll(page, pageSize);
    }

    //  Obtener usuarios ordenados por nombre
    @Operation(summary = "Get all users ordered by username")
    @ApiResponse(responseCode = "200", description = "Found users",
        content = @Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = User.class))))
    @GetMapping("orderByUsername")
    public List<User> getAllOrderByUsername() {
        return service.getAllOrderByUsername();
    }

    //  Obtener usuario por ID
    @Operation(summary = "Get a user by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("{idUser}")
    public ResponseEntity<User> getById(@PathVariable Integer idUser) {
        User user = service.getById(idUser);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //  Buscar usuario por username (para login)
    @Operation(summary = "Get a user by username (for login)")
    @ApiResponse(responseCode = "200", description = "User found",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = User.class)))
    @GetMapping("/search/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = service.getByUsername(username);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //  Registrar o actualizar usuario
    @Operation(summary = "Register or update a user")
    @ApiResponse(responseCode = "201", description = "User created or updated",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = UserRequestDTO.class)))
    @PostMapping
    public ResponseEntity<UserRequestDTO> add(@RequestBody UserRequestDTO userDTO) {
        User savedUser = service.save(convertToEntity(userDTO));
        UserRequestDTO response = convertToDTO(savedUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //  Conversión entre DTO y Entity
    private UserRequestDTO convertToDTO(User user) {
        return modelMapper.map(user, UserRequestDTO.class);
    }

    private User convertToEntity(UserRequestDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
