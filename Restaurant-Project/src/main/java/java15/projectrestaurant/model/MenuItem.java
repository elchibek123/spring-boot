package java15.projectrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
public class MenuItem extends BaseEntity {
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian = false;

    @JsonIgnore
    @OneToOne(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private StopList stopList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JsonIgnore
    @ManyToMany(mappedBy = "menuItems")
    private List<Cheque> cheques;

    public boolean isStopListed() {
        return stopList != null && stopList.isActive();
    }
}
