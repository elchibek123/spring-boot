package java15.projectrestaurant.model;

import jakarta.persistence.*;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.enums.RoleRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "role_requests")
public class RoleRequest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int experience;

    @Enumerated(EnumType.STRING)
    private RoleRequestStatus status;

    private String message;
    private String adminComment;
    private LocalDateTime requestDate = LocalDateTime.now();
}
