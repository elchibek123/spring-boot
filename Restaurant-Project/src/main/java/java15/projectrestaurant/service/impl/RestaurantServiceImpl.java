package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.RestaurantRequest;
import java15.projectrestaurant.dto.request.UpdateRestaurantRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.RestaurantView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.mapper.RestaurantProfileViewMapper;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.repository.RestaurantRepository;
import java15.projectrestaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantProfileViewMapper restaurantProfileViewMapper;

    @Override
    public RestaurantView createRestaurant(RestaurantRequest request) {
        if (restaurantRepository.existsByName(request.name())) {
            throw new BadRequestException("Restaurant with name '" + request.name() + "' already exists.");
        }

        Restaurant restaurant = request.toEntity(new Restaurant());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return restaurantProfileViewMapper.toView(savedRestaurant);
    }

    @Override
    public PaginationResponse<RestaurantView> getRestaurants(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<RestaurantView> restaurantPage = restaurantRepository.findAll(pageRequest)
                .map(restaurantProfileViewMapper::toView);

        return new PaginationResponse<RestaurantView>().setValuesTo(restaurantPage);
    }

    @Override
    public RestaurantView updateRestaurant(Long id, UpdateRestaurantRequest request) {
        if (restaurantRepository.existsByName(request.name())) {
            throw new BadRequestException("Restaurant with name '" + request.name() + "' already exists.");
        }

        Restaurant restaurant = restaurantRepository.findByIdOrThrow(id);

        Restaurant updatedRestaurant = request.toEntity(restaurant);

        Restaurant savedRestaurant = restaurantRepository.save(updatedRestaurant);

        return restaurantProfileViewMapper.toView(savedRestaurant);
    }

    @Override
    public void deleteRestaurant(Long id) {
        boolean existsById = restaurantRepository.existsById(id);

        if (!existsById) {
            throw new NotFoundException("Restaurant with ID: " + id + " not found.");
        }

        restaurantRepository.deleteById(id);
    }

    public RestaurantView getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findByIdOrThrow(id);

        return restaurantProfileViewMapper.toView(restaurant);
    }
}
