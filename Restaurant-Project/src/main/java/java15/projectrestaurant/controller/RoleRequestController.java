package java15.projectrestaurant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.RoleRequestDto;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RoleRequestView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.enums.RoleRequestStatus;
import java15.projectrestaurant.service.RoleRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
@Tag(name = "Role Request Controller", description = "API for managing user role requests")
@RequiredArgsConstructor
public class RoleRequestController {
    private final RoleRequestService roleRequestService;

    @PostMapping("/request")
    public ResponseEntity<SimpleResponseMessage> requestRole(
            @RequestParam Long restaurantId,
            @Valid @RequestBody RoleRequestDto request) {
        return ResponseEntity.ok(roleRequestService.requestRole(restaurantId, request));
    }

    @PostMapping("/{requestId}/process")
    @PreAuthorize("hasRole('ADMIN')")
    public SimpleResponseMessage processRequest(
            @PathVariable Long requestId,
            @RequestParam RoleRequestStatus decision,
            @RequestParam(required = false) String adminComment) {
        return roleRequestService.processRequest(requestId, decision, adminComment);
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginationResponse<RoleRequestView>> getPendingRequests(
            @RequestParam Long restaurantId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(roleRequestService.getPendingRequests(restaurantId, pageNumber, pageSize));
    }

    @GetMapping("/myRequests")
    public ResponseEntity<PaginationResponse<RoleRequestView>> getUserRequests(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(roleRequestService.getUserRequests(pageNumber, pageSize));
    }
}
