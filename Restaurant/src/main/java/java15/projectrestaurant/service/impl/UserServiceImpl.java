package java15.projectrestaurant.service.impl;

import jakarta.transaction.Transactional;
import java15.projectrestaurant.dto.request.CreateUserRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.UserProfileView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.UserProfileViewMapper;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.RestaurantRepository;
import java15.projectrestaurant.repository.UserRepository;
import java15.projectrestaurant.service.UserService;
import java15.projectrestaurant.validation.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRequestValidator createUserRequestValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileViewMapper userProfileViewMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    public PaginationResponse<UserProfileView> getUsers(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<UserProfileView> userPage = userRepository.findAll(pageRequest)
                .map(userProfileViewMapper::toView);

        return new PaginationResponse<UserProfileView>().setValuesTo(userPage);
    }

    @Override
    @Transactional
    public UserProfileView createUser(CreateUserRequest request, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdOrThrow(restaurantId);

        if (restaurant.getUsers().size() >= 15) {
            throw new BadRequestException("The restaurant has reached its user capacity of 15.");
        }

        createUserRequestValidator.validate(request, CreateUserRequest::email);

        User user = request.toEntity(new User(), passwordEncoder);

        user.setRestaurant(restaurant);

        restaurant.getUsers().add(user);

        User savedUser = userRepository.save(user);

        restaurantRepository.save(restaurant);

        return userProfileViewMapper.toView(savedUser);
    }

    @Override
    public PaginationResponse<UserProfileView> getUsersByRestaurantId(int pageNumber, int pageSize, Long restaurantId) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<User> userPage = userRepository.findByRestaurantId(restaurantId, pageRequest);

        Page<UserProfileView> userProfileViews = userPage.map(userProfileViewMapper::toView);

        return new PaginationResponse<UserProfileView>().setValuesTo(userProfileViews);
    }
}
