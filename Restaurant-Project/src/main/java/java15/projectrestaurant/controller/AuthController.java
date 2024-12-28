package java15.projectrestaurant.controller;

import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.AuthRequest;
import java15.projectrestaurant.dto.request.RegisterRequest;
import java15.projectrestaurant.dto.response.LoginView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public SimpleResponseMessage register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginView login(@Valid @RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
