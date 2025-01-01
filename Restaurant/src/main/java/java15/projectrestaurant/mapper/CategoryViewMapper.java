package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.CategoryView;
import java15.projectrestaurant.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryViewMapper {

    public CategoryView toView(Category category) {
        if (category == null) {
            return CategoryView.builder().build();
        }

        return CategoryView.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
