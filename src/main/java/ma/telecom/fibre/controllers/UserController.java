package ma.telecom.fibre.controllers;

import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.UserRequest;
import ma.telecom.fibre.dtos.UserResponse;
import ma.telecom.fibre.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllTechniciens() {
        return ResponseEntity.ok(userService.getAllTechniciens());
    }

    @PostMapping
    public ResponseEntity<UserResponse> createTechnicien(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createTechnicien(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateTechnicien(@PathVariable String id, @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateTechnicien(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTechnicien(@PathVariable String id, Authentication authentication) {
        userService.deleteTechnicien(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
