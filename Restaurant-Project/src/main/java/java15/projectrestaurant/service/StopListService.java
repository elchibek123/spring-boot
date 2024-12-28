package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.StopListRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.StopListView;

public interface StopListService {
    StopListView createStopList(StopListRequest request, Long menuItemId);

    StopListView updateStopList(Long id, StopListRequest request);

    void deleteStopList(Long id);

    PaginationResponse<StopListView> getStopLists(int pageNumber, int pageSize);

    StopListView getById(Long id);
}
