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

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean active = true;

    @OneToOne
    @JoinColumn(name = "menu_item_id", unique = true, nullable = false)
    private MenuItem menuItem;

    @PrePersist
    @PreUpdate
    protected void updateDate() {
        this.date = LocalDate.now();
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            this.date = LocalDate.now();
        }
    }
}
