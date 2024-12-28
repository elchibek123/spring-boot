package java15.projectrestaurant.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "stop_lists")
public class StopList extends BaseEntity {
    private String reason;
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @PrePersist
    @PreUpdate
    protected void updateDate() {
        this.date = LocalDate.now();
    }
}
