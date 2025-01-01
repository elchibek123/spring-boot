package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.CreateUserRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.UserProfileView;

public interface UserService {
    PaginationResponse<UserProfileView> getUsers(int pageNumber, int pageSize);

    UserProfileView createUser(CreateUserRequest request, Long restaurantId);

    PaginationResponse<UserProfileView> getUsersByRestaurantId(int pageNumber, int pageSize, Long restaurantId);
}
