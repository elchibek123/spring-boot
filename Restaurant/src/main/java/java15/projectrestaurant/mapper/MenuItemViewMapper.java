package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.MenuItemView;
import java15.projectrestaurant.model.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemViewMapper {

    public MenuItemView toView(MenuItem menuItem) {
        if (menuItem == null) {
            return MenuItemView.builder().build();
        }

        Long subcategoryId = (menuItem.getSubcategory() != null) ? menuItem.getSubcategory().getId() : null;

        return MenuItemView.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .imageUrl(menuItem.getImageUrl())
                .subcategoryId(subcategoryId)
                .build();
    }
}
