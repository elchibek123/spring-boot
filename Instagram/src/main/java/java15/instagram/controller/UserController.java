package java15.instagram.controller;

import jakarta.validation.Valid;
import java15.instagram.model.dto.request.UserRequest;
import java15.instagram.model.dto.response.SimpleResponseMessage;
import java15.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public SimpleResponseMessage register(@Valid @RequestBody UserRequest request) {
        return userService.register(request);
    }
}
