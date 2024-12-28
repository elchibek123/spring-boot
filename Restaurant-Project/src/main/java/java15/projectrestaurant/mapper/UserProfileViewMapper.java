package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.UserProfileView;
import java15.projectrestaurant.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserProfileViewMapper {

    public UserProfileView toView(User user) {
        if (user == null) {
            return UserProfileView.builder().build();
        }

        return UserProfileView.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .experience(user.getExperience())
                .build();
    }
}

