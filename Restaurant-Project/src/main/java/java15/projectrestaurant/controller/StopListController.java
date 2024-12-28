package java15.projectrestaurant.controller;

import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.StopListRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.dto.response.StopListView;
import java15.projectrestaurant.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/stopLists")
@RequiredArgsConstructor
public class StopListController {
    private final StopListService stopListService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{menuItemId}")
    public ResponseEntity<StopListView> createStopList(
            @Valid @RequestBody StopListRequest request,
            @PathVariable Long menuItemId) {
        return ResponseEntity.ok(stopListService.createStopList(request, menuItemId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{id}")
    public ResponseEntity<StopListView> updateStopList(
            @PathVariable Long id,
            @Valid @RequestBody StopListRequest request) {
        return ResponseEntity.ok(stopListService.updateStopList(id, request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}")
    public SimpleResponseMessage deleteStopList(@PathVariable Long id) {
        stopListService.deleteStopList(id);
        return new SimpleResponseMessage("StopList successfully deleted");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping
    public PaginationResponse<StopListView> getStopLists(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return stopListService.getStopLists(pageNumber, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{id}")
    public StopListView getById(@PathVariable Long id) {
        return stopListService.getById(id);
    }
}
