package java15.projectrestaurant.dto.request;

import java15.projectrestaurant.model.StopList;

public record StopListRequest(
        String reason
) {
    public StopList applyTo(StopList stopList) {
        stopList.setReason(this.reason);
        return stopList;
    }

    public StopList toEntity() {
        StopList stopList = new StopList();
        return applyTo(stopList);
    }
}
