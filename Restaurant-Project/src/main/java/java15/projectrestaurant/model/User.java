package java15.projectrestaurant.model;

import jakarta.persistence.*;
import java15.projectrestaurant.enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int experience;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cheque> cheques = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoleRequest> roleRequests = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getAuthority()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
