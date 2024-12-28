package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.StopListRequest;
import java15.projectrestaurant.dto.response.PaginationResponse;
import java15.projectrestaurant.dto.response.StopListView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.StopListViewMapper;
import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.StopList;
import java15.projectrestaurant.repository.MenuItemRepository;
import java15.projectrestaurant.repository.StopListRepository;
import java15.projectrestaurant.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListViewMapper stopListViewMapper;

    @Override
    public StopListView createStopList(StopListRequest request, Long menuItemId) {
        if (request == null || menuItemId == null) {
            throw new BadRequestException("Request and menuItem ID cannot be null");
        }

        MenuItem menuItem = menuItemRepository.findByIdOrThrow(menuItemId);

        LocalDate today = LocalDate.now();
        boolean exists = stopListRepository.existsByMenuItemAndDate(menuItem, today);
        if (exists) {
            throw new BadRequestException("StopList for this menu item already exists today");
        }

        StopList stopList = request.toEntity();
        stopList.setMenuItem(menuItem);
        stopList.setDate(today);

        StopList savedStopList = stopListRepository.save(stopList);

        return stopListViewMapper.toView(savedStopList);
    }

    @Override
    public StopListView updateStopList(Long id, StopListRequest request) {
        if (id == null || request == null) {
            throw new BadRequestException("ID and request cannot be null.");
        }

        StopList existingStopList = stopListRepository.findByIdOrThrow(id);

        if (request.reason() != null && !request.reason().isBlank()) {
            existingStopList.setReason(request.reason());
        } else {
            throw new BadRequestException("Reason cannot be null or empty.");
        }

        StopList updatedStopList = stopListRepository.save(existingStopList);

        return stopListViewMapper.toView(updatedStopList);
    }


    @Override
    public void deleteStopList(Long id) {
        if (id == null) {
            throw new BadRequestException("StopList ID cannot be null");
        }

        StopList stopList = stopListRepository.findByIdOrThrow(id);

        MenuItem menuItem = stopList.getMenuItem();
        if (menuItem != null) {
            menuItem.setStopList(null);
        }

        stopListRepository.delete(stopList);
    }


    @Override
    public PaginationResponse<StopListView> getStopLists(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);

        Page<StopListView> stopListPage = stopListRepository.findAll(pageRequest)
                .map(stopListViewMapper::toView);

        return new PaginationResponse<StopListView>().setValuesTo(stopListPage);
    }

    @Override
    public StopListView getById(Long id) {
        StopList stopList = stopListRepository.findByIdOrThrow(id);

        return stopListViewMapper.toView(stopList);
    }
}
