package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.AuthRequest;
import java15.projectrestaurant.dto.request.RegisterRequest;
import java15.projectrestaurant.dto.response.LoginView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;

public interface AuthService {
    LoginView login(AuthRequest request);

    SimpleResponseMessage register(RegisterRequest request);
}
