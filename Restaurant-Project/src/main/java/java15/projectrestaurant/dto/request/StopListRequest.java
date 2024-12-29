package java15.projectrestaurant.dto.request;

import java15.projectrestaurant.model.StopList;

public record StopListRequest(
        String reason,
        Boolean active
) {
    public StopList toEntity() {
        StopList stopList = new StopList();
        stopList.setReason(reason);
        if (active != null) {
            stopList.setActive(active);
        }
        return stopList;
    }
}
