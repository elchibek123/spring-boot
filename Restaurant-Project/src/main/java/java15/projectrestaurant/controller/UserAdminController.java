package java15.projectrestaurant.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.CreateUserRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.UserProfileView;
import java15.projectrestaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
@RolesAllowed("ADMIN")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @PostMapping("/{restaurantId}")
    public UserProfileView create(
            @Valid @RequestBody CreateUserRequest request,
            @PathVariable Long restaurantId) {
        return userService.createUser(request, restaurantId);
    }

    @GetMapping
    public PaginationResponse<UserProfileView> getUsers(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return userService.getUsers(pageNumber, pageSize);
    }

    @GetMapping("/{restaurantId}")
    public PaginationResponse<UserProfileView> getUsersByRestaurantId (
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return userService.getUsersByRestaurantId(pageNumber, pageSize, restaurantId);
    }
}
