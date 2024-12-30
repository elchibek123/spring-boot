package java15.instagram.service;

import java15.instagram.model.dto.request.AuthRequest;
import java15.instagram.model.dto.response.LoginView;

public interface AuthService {
    LoginView login(AuthRequest request);
}
