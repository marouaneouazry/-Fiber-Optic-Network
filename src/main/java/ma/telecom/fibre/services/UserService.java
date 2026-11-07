package ma.telecom.fibre.services;

import lombok.RequiredArgsConstructor;
import ma.telecom.fibre.dtos.UserRequest;
import ma.telecom.fibre.dtos.UserResponse;
import ma.telecom.fibre.exceptions.BusinessRuleException;
import ma.telecom.fibre.models.Role;
import ma.telecom.fibre.models.User;
import ma.telecom.fibre.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllTechniciens() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.TECHNICIEN)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse createTechnicien(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessRuleException("Ce nom d'utilisateur existe déjà");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Cet email est déjà utilisé");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BusinessRuleException("Le mot de passe est obligatoire pour la création");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TECHNICIEN)
                .actif(request.isActif())
                .build();

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    public UserResponse updateTechnicien(String id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Utilisateur non trouvé"));

        if (user.getRole() == Role.ADMIN) {
            throw new BusinessRuleException("La modification d'un compte ADMIN est interdite");
        }

        // Check unique constraints for username
        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessRuleException("Ce nom d'utilisateur existe déjà");
        }
        // Check unique constraints for email
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Cet email est déjà utilisé");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setActif(request.isActif());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    public void deleteTechnicien(String id, String currentUsername) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessRuleException("Utilisateur non trouvé"));

        if (user.getRole() == Role.ADMIN) {
            throw new BusinessRuleException("La suppression d'un compte ADMIN est interdite");
        }

        if (user.getUsername().equals(currentUsername)) {
            throw new BusinessRuleException("Vous ne pouvez pas supprimer votre propre compte");
        }

        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .actif(user.isActif())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
