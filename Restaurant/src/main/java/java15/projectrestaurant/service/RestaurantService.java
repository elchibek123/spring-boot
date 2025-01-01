package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.RestaurantRequest;
import java15.projectrestaurant.dto.request.UpdateRestaurantRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RestaurantView;

public interface RestaurantService {
    RestaurantView createRestaurant(RestaurantRequest request);

    PaginationResponse<RestaurantView> getRestaurants(int pageNumber, int pageSize);

    RestaurantView updateRestaurant(Long id, UpdateRestaurantRequest request);

    void deleteRestaurant(Long id);

    RestaurantView getRestaurantById(Long id);
}
