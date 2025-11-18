package ws.beauty.salon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ws.beauty.salon.dto.LoginUserDto;
import ws.beauty.salon.dto.RegisterUserDto;
import ws.beauty.salon.model.User;
import ws.beauty.salon.dto.LoginResponse;
import ws.beauty.salon.service.AuthenticationService;
import ws.beauty.salon.service.JwtService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints for login and registration")
public class AuthenticationController {
    
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    
    @Operation(summary = "Register a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = User.class)))
    @PostMapping("/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Login user and get JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = LoginResponse.class)))
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        
        String jwtToken = jwtService.generateToken(authenticatedUser);
        
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .username(authenticatedUser.getUsername())
                .role(authenticatedUser.getRole())
                .build();
        
        return ResponseEntity.ok(loginResponse);
    }
}