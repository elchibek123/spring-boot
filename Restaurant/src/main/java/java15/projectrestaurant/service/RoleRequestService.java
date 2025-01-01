package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.RoleRequestDto;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RoleRequestView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.enums.RoleRequestStatus;

public interface RoleRequestService {
    SimpleResponseMessage requestRole(Long restaurantId, RoleRequestDto request);

    SimpleResponseMessage processRequest(Long requestId, RoleRequestStatus decision, String adminComment);

    PaginationResponse<RoleRequestView> getUserRequests(int pageNumber, int pageSize);

    PaginationResponse<RoleRequestView> getPendingRequests(Long restaurantId, int pageNumber, int pageSize);
}
