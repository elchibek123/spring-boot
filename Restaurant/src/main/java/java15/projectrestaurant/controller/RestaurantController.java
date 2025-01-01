package java15.projectrestaurant.controller;

import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.RestaurantRequest;
import java15.projectrestaurant.dto.request.UpdateRestaurantRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RestaurantView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/restaurants")
@RequiredArgsConstructor
@Secured("ADMIN")
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantView> createRestaurant(
            @Valid @RequestBody RestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.createRestaurant(request));
    }

    @GetMapping
    public PaginationResponse<RestaurantView> getRestaurants(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return restaurantService.getRestaurants(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public RestaurantView getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantView> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, request));
    }

    @DeleteMapping("/{id}")
    public SimpleResponseMessage deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return new SimpleResponseMessage("Restaurant successfully deleted.");
    }
}
