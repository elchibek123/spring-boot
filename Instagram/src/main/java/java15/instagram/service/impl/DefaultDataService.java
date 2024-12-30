package java15.instagram.service.impl;

import jakarta.annotation.PostConstruct;
import java15.instagram.model.entity.User;
import java15.instagram.model.enums.Role;
import java15.instagram.repository.UserRepository;
import java15.instagram.util.CommonConstants;
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
        String email = CommonConstants.ADMIN_EMAIL;
        boolean exists = userRepository.existsByEmail(email);
        if (!exists) {
            userRepository.save(
                    User.builder()
                            .username(CommonConstants.ADMIN_USERNAME)
                            .email(email)
                            .password(passwordEncoder.encode(CommonConstants.ADMIN_PASSWORD))
                            .role(Role.ADMIN)
                            .build()
            );
        }
    }
}
