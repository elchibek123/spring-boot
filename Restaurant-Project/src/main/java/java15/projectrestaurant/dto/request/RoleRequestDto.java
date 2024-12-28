package java15.projectrestaurant.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import java15.projectrestaurant.enums.Role;
import java15.projectrestaurant.model.RoleRequest;

public record RoleRequestDto(
        @NotNull
        @Enumerated(EnumType.STRING)
        Role role,

        @NotNull
        @PositiveOrZero
        int experience,

        String message
) {
    public RoleRequest toEntity(java15.projectrestaurant.model.RoleRequest roleRequest) {
        roleRequest.setRole(this.role);
        roleRequest.setExperience(this.experience);
        roleRequest.setMessage(this.message);

        return roleRequest;
    }
}
