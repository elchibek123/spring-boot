package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.MenuItemRequest;
import java15.projectrestaurant.dto.request.UpdateMenuItemRequest;
import java15.projectrestaurant.dto.response.MenuItemView;
import java15.projectrestaurant.dto.response.PaginationResponse;

public interface MenuItemService {
    MenuItemView createMenuItemInSubcategory(MenuItemRequest request, Long restaurantId, Long subcategoryId);

    PaginationResponse<MenuItemView> getMenuItems(Long restaurantId, int pageNumber, int pageSize);

    MenuItemView getMenuItemById(Long id);

    MenuItemView updateMenuItem(Long id, UpdateMenuItemRequest request, Long subcategoryId);

    MenuItemView createMenuItem(MenuItemRequest request, Long restaurantId);

    void deleteMenuItem(Long id);

    PaginationResponse<MenuItemView> searchMenuItems(String query, int pageNumber, int pageSize);

    PaginationResponse<MenuItemView> sortByPrice(int pageNumber, int pageSize, String sortDirection);

    PaginationResponse<MenuItemView> isVegetarian(int pageNumber, int pageSize, String isVegetarian);
}
