package java15.instagram.controller;

import jakarta.validation.Valid;
import java15.instagram.model.dto.request.AuthRequest;
import java15.instagram.model.dto.response.LoginView;
import java15.instagram.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginView login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
