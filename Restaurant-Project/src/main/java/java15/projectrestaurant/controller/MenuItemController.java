package java15.projectrestaurant.controller;

import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.MenuItemRequest;
import java15.projectrestaurant.dto.request.UpdateMenuItemRequest;
import java15.projectrestaurant.dto.response.MenuItemView;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/menuItems")
@RequiredArgsConstructor
public class MenuItemController {
    private final MenuItemService menuItemService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{restaurantId}")
    public ResponseEntity<MenuItemView> createMenuItemInSubcategory(
            @Valid @RequestBody MenuItemRequest request,
            @PathVariable Long restaurantId,
            @RequestParam Long subcategoryId) {
        MenuItemView menuItem = menuItemService.createMenuItemInSubcategory(request, restaurantId, subcategoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PostMapping("/{restaurantId}/create")
    public ResponseEntity<MenuItemView> createMenuItem(
            @Valid @RequestBody MenuItemRequest request,
            @PathVariable Long restaurantId) {
        MenuItemView menuItem = menuItemService.createMenuItem(request, restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/{restaurantId}")
    public PaginationResponse<MenuItemView> getMenuItems(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return menuItemService.getMenuItems(restaurantId ,pageNumber, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/getById/{id}")
    public MenuItemView getMenuItemById(@PathVariable Long id) {
        return menuItemService.getMenuItemById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @PutMapping("/{id}")
    public ResponseEntity<MenuItemView> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMenuItemRequest request,
            @RequestParam(required = false) Long subcategoryId) {
        return ResponseEntity.ok(menuItemService.updateMenuItem(id, request, subcategoryId));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/search")
    public PaginationResponse<MenuItemView> searchMenuItems(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return menuItemService.searchMenuItems(query, pageNumber, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/sortByPrice")
    public PaginationResponse<MenuItemView> sortByPrice(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        return menuItemService.sortByPrice(pageNumber, pageSize, sortDirection);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF', 'WAITER')")
    @GetMapping("/isVegetarian")
    public PaginationResponse<MenuItemView> isVegetarian(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "false") String isVegetarian) {
        return menuItemService.isVegetarian(pageNumber, pageSize, isVegetarian);
    }
}
