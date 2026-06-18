package ma.telecom.fibre.services;


import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.LoginRequest;
import ma.telecom.fibre.dtos.RegisterRequest;
import ma.telecom.fibre.dtos.JwtResponse;
import ma.telecom.fibre.exceptions.BusinessRuleException;
import ma.telecom.fibre.models.Role;
import ma.telecom.fibre.models.User;
import ma.telecom.fibre.repositories.UserRepository;
import ma.telecom.fibre.security.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public JwtResponse login(LoginRequest request) {
        // Authentifie via Spring Security
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtils.generateToken(userDetails);

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new JwtResponse(token, user.getUsername(), user.getRole().name());
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new BusinessRuleException("Ce nom d'utilisateur existe déjà");
        if (userRepository.existsByEmail(request.getEmail()))
            throw new BusinessRuleException("Cet email est déjà utilisé");

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(request.getRole() != null ? request.getRole() : Role.TECHNICIEN)
                .build();

        userRepository.save(user);
    }
}