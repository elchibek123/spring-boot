package java15.projectrestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "subcategories")
public class Subcategory extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "subcategory")
    private List<MenuItem> menuItems;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
