package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.RoleRequestDto;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RoleRequestView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.enums.RoleRequestStatus;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.RoleRequestViewMapper;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.model.RoleRequest;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.RestaurantRepository;
import java15.projectrestaurant.repository.UserRepository;
import java15.projectrestaurant.repository.RoleRequestRepository;
import java15.projectrestaurant.service.RoleRequestService;
import java15.projectrestaurant.util.SecurityUtils;
import java15.projectrestaurant.validation.validator.RoleRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleRequestServiceImpl implements RoleRequestService {
    private final RoleRequestRepository roleRequestRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RoleRequestValidator roleRequestValidator;
    private final RoleRequestViewMapper roleRequestViewMapper;

    @Override
    public SimpleResponseMessage requestRole(Long restaurantId, RoleRequestDto request) {
        Restaurant restaurant = restaurantRepository.findByIdOrThrow(restaurantId);

        String username = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmailOrThrow(username);

        if (roleRequestRepository.existsByUserAndRestaurantAndStatus(
                user, restaurant, RoleRequestStatus.PENDING)) {
            throw new BadRequestException("You already have a pending request for this restaurant");
        }

        roleRequestValidator.validate(request, user);

        java15.projectrestaurant.model.RoleRequest roleRequest = request.toEntity(new java15.projectrestaurant.model.RoleRequest());
        roleRequest.setUser(user);
        roleRequest.setRestaurant(restaurant);
        roleRequest.setStatus(RoleRequestStatus.PENDING);

        roleRequestRepository.save(roleRequest);

        return new SimpleResponseMessage("Role request submitted successfully");
    }

    @Override
    public SimpleResponseMessage processRequest(Long requestId, RoleRequestStatus decision, String adminComment) {
        java15.projectrestaurant.model.RoleRequest roleRequest = roleRequestRepository.findByIdOrThrow(requestId);

        if (roleRequest.getStatus() != RoleRequestStatus.PENDING) {
            throw new BadRequestException("This request has already been processed");
        }

        roleRequest.setStatus(decision);
        roleRequest.setAdminComment(adminComment);

        if (decision == RoleRequestStatus.APPROVED) {
            User user = roleRequest.getUser();
            Restaurant restaurant = roleRequest.getRestaurant();

            user.setRole(roleRequest.getRole());
            user.setRestaurant(restaurant);

            restaurant.getUsers().add(user);

            userRepository.save(user);
            restaurantRepository.save(restaurant);

            return new SimpleResponseMessage("Role request approved. User has been updated.");
        }

        roleRequestRepository.save(roleRequest);

        return new SimpleResponseMessage("Role request rejected.");
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationResponse<RoleRequestView> getUserRequests(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        String username = SecurityUtils.getCurrentUser();

        User currentUser = userRepository.findByEmailOrThrow(username);

        Page<RoleRequest> roleRequestPage = roleRequestRepository.findByUser(currentUser, pageRequest);

        Page<RoleRequestView> roleRequestViewPage = roleRequestPage.map(roleRequestViewMapper::toView);

        return new PaginationResponse<RoleRequestView>().setValuesTo(roleRequestViewPage);
    }

    @Override
    public PaginationResponse<RoleRequestView> getPendingRequests(Long restaurantId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<RoleRequest> roleRequestPage = roleRequestRepository.findByRestaurantIdAndStatus(
                restaurantId,
                RoleRequestStatus.PENDING,
                pageRequest
        );

        Page<RoleRequestView> roleRequestViewPage = roleRequestPage.map(roleRequestViewMapper::toView);

        return new PaginationResponse<RoleRequestView>().setValuesTo(roleRequestViewPage);
    }
}
