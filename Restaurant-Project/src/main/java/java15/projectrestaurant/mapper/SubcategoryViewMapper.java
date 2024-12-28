package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.SubcategoryView;
import java15.projectrestaurant.model.Subcategory;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryViewMapper {

    public SubcategoryView toView(Subcategory subcategory) {
        if (subcategory == null) {
            return SubcategoryView.builder().build();
        }

        return SubcategoryView.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .categoryId(subcategory.getCategory().getId())
                .build();
    }
}
