package java15.projectrestaurant.model;

import jakarta.persistence.*;
import java15.projectrestaurant.enums.RestaurantType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    private RestaurantType restaurantType;

    @Column(nullable = false)
    private int numberOfEmployees;

    @Column(nullable = false)
    private int serviceFee;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant")
    private List<RoleRequest> roleRequests;

    public int getNumberOfEmployees() {
        return (users == null) ? numberOfEmployees : users.size();
    }
}
