package java15.instagram.service.mapper;

import java15.instagram.config.jwt.JwtToken;
import java15.instagram.model.dto.response.LoginView;
import java15.instagram.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class LoginViewMapper {

    public LoginView toView(User user, JwtToken token) {
        if (user == null) {
            return LoginView.builder().build();
        }

        return LoginView.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accessToken(token)
                .build();
    }
}
