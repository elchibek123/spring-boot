package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.RoleRequestView;
import java15.projectrestaurant.model.RoleRequest;
import org.springframework.stereotype.Component;

@Component
public class RoleRequestViewMapper {

    public RoleRequestView toView(RoleRequest roleRequest) {
        if (roleRequest == null) {
            return RoleRequestView.builder().build();
        }

        return RoleRequestView.builder()
                .id(roleRequest.getId())
                .userId(roleRequest.getUser().getId())
                .userName(roleRequest.getUser().getFirstName() + " " + roleRequest.getUser().getLastName())
                .role(roleRequest.getRole())
                .status(roleRequest.getStatus())
                .requestDate(roleRequest.getRequestDate())
                .message(roleRequest.getMessage())
                .build();
    }
}
