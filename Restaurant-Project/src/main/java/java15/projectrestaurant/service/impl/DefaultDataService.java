package java15.projectrestaurant.service.impl;

import jakarta.annotation.PostConstruct;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultDataService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void saveDefaultAdmin() {
        String email = "admin@gmail.com";
        boolean exists = userRepository.existsByEmail(email);
        if (!exists) {
            userRepository.save(
                    User.builder()
                            .firstName("Admin")
                            .email(email)
                            .password(passwordEncoder.encode("admin"))
                            .role(Role.ADMIN)
                            .build()
            );
        }
    }
}
