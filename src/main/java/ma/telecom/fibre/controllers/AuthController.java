package ma.telecom.fibre.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.LoginRequest;
import ma.telecom.fibre.dtos.RegisterRequest;
import ma.telecom.fibre.dtos.ApiResponse;
import ma.telecom.fibre.dtos.JwtResponse;
import ma.telecom.fibre.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(201).body(new ApiResponse(true, "Utilisateur créé avec succès"));
    }
}