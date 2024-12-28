package java15.projectrestaurant.mapper;

import java15.projectrestaurant.dto.response.StopListView;
import java15.projectrestaurant.model.StopList;
import org.springframework.stereotype.Component;

@Component
public class StopListViewMapper {

    public StopListView toView(StopList stopList) {
        if (stopList == null) {
            return StopListView.builder().build();
        }

        return StopListView.builder()
                .id(stopList.getId())
                .reason(stopList.getReason())
                .menuItemName(stopList.getMenuItem().getName())
                .date(stopList.getDate())
                .build();
    }
}
