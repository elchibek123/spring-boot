package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.MenuItemRequest;
import java15.projectrestaurant.dto.request.UpdateMenuItemRequest;
import java15.projectrestaurant.dto.response.MenuItemView;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.MenuItemViewMapper;
import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.model.Subcategory;
import java15.projectrestaurant.repository.MenuItemRepository;
import java15.projectrestaurant.repository.RestaurantRepository;
import java15.projectrestaurant.repository.SubcategoryRepository;
import java15.projectrestaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final MenuItemViewMapper menuItemViewMapper;

    @Override
    @Transactional
    public MenuItemView createMenuItemInSubcategory(MenuItemRequest request, Long restaurantId, Long subcategoryId) {
        if (request == null || restaurantId == null || subcategoryId == null) {
            throw new BadRequestException("Request, restaurant ID, and subcategory ID cannot be null");
        }

        Restaurant restaurant = restaurantRepository.findByIdOrThrow(restaurantId);
        Subcategory subcategory = subcategoryRepository.findByIdOrThrow(subcategoryId);

        boolean exists = menuItemRepository.existsByNameAndRestaurantIdAndSubcategoryId(
                request.name(),
                restaurantId,
                subcategoryId
        );

        if (exists) {
            throw new BadRequestException(
                    String.format("Menu item with name '%s' already exists for restaurant '%s' in subcategory '%s'.",
                            request.name(),
                            restaurant.getName(),
                            subcategory.getName())
            );
        }

        MenuItem menuItem = request.toEntity();
        menuItem.setSubcategory(subcategory);
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemViewMapper.toView(savedMenuItem);
    }

    @Override
    public PaginationResponse<MenuItemView> getMenuItems(Long restaurantId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<MenuItemView> menuItemPage = menuItemRepository.findAllAvailableByRestaurantId(restaurantId, pageRequest)
                .map(menuItemViewMapper::toView);

        return new PaginationResponse<MenuItemView>().setValuesTo(menuItemPage);
    }

    @Override
    public MenuItemView getMenuItemById(Long id) {
        menuItemRepository.findByIdOrThrow(id);

        return menuItemViewMapper.toView(menuItemRepository.findByIdOrThrow(id));
    }

    @Override
    @Transactional
    public MenuItemView updateMenuItem(Long id, UpdateMenuItemRequest request, Long subcategoryId) {
        if (id == null || request == null) {
            throw new BadRequestException("Menu item ID and request data cannot be null.");
        }

        MenuItem existingMenuItem = menuItemRepository.findByIdOrThrow(id);

        boolean exists = menuItemRepository.existsByNameAndRestaurantIdAndSubcategoryIdAndIdNot(
                request.name(),
                existingMenuItem.getRestaurant().getId(),
                subcategoryId,
                id
        );

        if (exists) {
            throw new BadRequestException(
                    String.format("Menu item with name '%s' already exists for restaurant '%s' in subcategory '%s'.",
                            request.name(),
                            existingMenuItem.getRestaurant().getName(),
                            existingMenuItem.getSubcategory() != null ? existingMenuItem.getSubcategory().getName() : "N/A")
            );
        }

        MenuItem updatedMenuItem = request.toEntity(existingMenuItem);

        if (subcategoryId != null &&
                (existingMenuItem.getSubcategory() == null ||
                        !existingMenuItem.getSubcategory().getId().equals(subcategoryId))) {
            Subcategory newSubcategory = subcategoryRepository.findByIdOrThrow(subcategoryId);
            existingMenuItem.setSubcategory(newSubcategory);
        }

        MenuItem savedMenuItem = menuItemRepository.save(updatedMenuItem);

        return menuItemViewMapper.toView(savedMenuItem);
    }

    @Override
    @Transactional
    public MenuItemView createMenuItem(MenuItemRequest request, Long restaurantId) {
        if (request == null || restaurantId == null) {
            throw new BadRequestException("Request, restaurant ID cannot be null");
        }

        Restaurant restaurant = restaurantRepository.findByIdOrThrow(restaurantId);

        MenuItem menuItem = request.toEntity();
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemViewMapper.toView(savedMenuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findByIdOrThrow(id);

        if (menuItem.getRestaurant() != null) {
            menuItem.getRestaurant().getMenuItems().remove(menuItem);
            menuItem.setRestaurant(null);
        }

        if (menuItem.getSubcategory() != null) {
            menuItem.getSubcategory().getMenuItems().remove(menuItem);
            menuItem.setSubcategory(null);
        }

        menuItemRepository.delete(menuItem);
    }

    @Override
    public PaginationResponse<MenuItemView> searchMenuItems(String query, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<MenuItem> menuItemsPage = menuItemRepository.searchAvailableByQuery(query, pageable);

        Page<MenuItemView> menuItemPage = menuItemsPage.map(menuItemViewMapper::toView);

        return new PaginationResponse<MenuItemView>().setValuesTo(menuItemPage);
    }

    @Override
    public PaginationResponse<MenuItemView> sortByPrice(int pageNumber, int pageSize, String sortDirection) {
        Sort sort = "desc".equalsIgnoreCase(sortDirection) ?
                Sort.by(Sort.Order.desc("price")) :
                Sort.by(Sort.Order.asc("price"));

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);

        Page<MenuItem> menuItemsPage = menuItemRepository.findAllAvailable(pageable);

        Page<MenuItemView> menuItemViewsPage = menuItemsPage.map(menuItemViewMapper::toView);

        return new PaginationResponse<MenuItemView>().setValuesTo(menuItemViewsPage);
    }

    @Override
    public PaginationResponse<MenuItemView> isVegetarian(int pageNumber, int pageSize, String isVegetarian) {
        boolean vegetarian = Boolean.parseBoolean(isVegetarian);

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        Page<MenuItem> menuItemsPage = menuItemRepository.findAvailableByIsVegetarian(vegetarian, pageable);

        Page<MenuItemView> menuItemViewsPage = menuItemsPage.map(menuItemViewMapper::toView);

        return new PaginationResponse<MenuItemView>().setValuesTo(menuItemViewsPage);
    }
}
