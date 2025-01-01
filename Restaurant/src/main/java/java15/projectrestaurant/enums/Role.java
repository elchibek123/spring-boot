package java15.projectrestaurant.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER,
    CHEF,
    WAITER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
