package java15.projectrestaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cheques")
public class Cheque extends BaseEntity {
    private BigDecimal priceAverage;
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "cheque_menuitem",
            joinColumns = @JoinColumn(name = "cheque_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private List<MenuItem> menuItems;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    @PreUpdate
    protected void updatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}
