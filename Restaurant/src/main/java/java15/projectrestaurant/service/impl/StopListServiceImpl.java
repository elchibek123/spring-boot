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
import java15.projectrestaurant.validation.validator.GenericRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;
    private final StopListViewMapper stopListViewMapper;

    @Override
    @Transactional
    public StopListView createStopList(StopListRequest request, Long menuItemId) {
        GenericRequestValidator.validateField(request, menuItemId, "MenuItem ID");

        MenuItem menuItem = menuItemRepository.findByIdOrThrow(menuItemId);

        Optional<StopList> existingActiveStopList = stopListRepository.findActiveByMenuItem(menuItem);
        if (existingActiveStopList.isPresent()) {
            throw new BadRequestException(
                    String.format("MenuItem '%s' is already in StopList since %s. Deactivate the current StopList before creating a new one.",
                            menuItem.getName(),
                            existingActiveStopList.get().getDate())
            );
        }

        stopListRepository.deactivateExistingStopLists(menuItemId);

        StopList stopList = request.toEntity();
        stopList.setMenuItem(menuItem);
        stopList.setDate(LocalDate.now());
        stopList.setActive(true);

        StopList savedStopList = stopListRepository.save(stopList);

        return stopListViewMapper.toView(savedStopList);
    }

    @Override
    @Transactional
    public StopListView updateStopList(Long id, StopListRequest request) {
        GenericRequestValidator.validateField(request, id, "ID");

        StopList existingStopList = stopListRepository.findByIdOrThrow(id);

        if (!existingStopList.isActive()) {
            throw new BadRequestException(
                    String.format("Cannot update inactive StopList from %s. Create a new StopList instead.",
                            existingStopList.getDate())
            );
        }

        if (request.reason() != null && !request.reason().isBlank()) {
            existingStopList.setReason(request.reason());
        } else {
            throw new BadRequestException("Reason cannot be null or empty.");
        }

        if (request.active() != null && request.active() != existingStopList.isActive()) {
            existingStopList.setActive(request.active());
            if (request.active()) {
                existingStopList.setDate(LocalDate.now());
            }
        }

        StopList updatedStopList = stopListRepository.save(existingStopList);

        return stopListViewMapper.toView(updatedStopList);
    }

    @Override
    @Transactional
    public void deleteStopList(Long id) {
        if (id == null) {
            throw new BadRequestException("StopList ID cannot be null");
        }

        StopList stopList = stopListRepository.findByIdOrThrow(id);

        if (!stopList.isActive()) {
            throw new BadRequestException(
                    String.format("Cannot delete inactive StopList from %s.", stopList.getDate())
            );
        }

        MenuItem menuItem = stopList.getMenuItem();
        if (menuItem != null) {
            menuItem.setStopList(null);
        }

        stopList.setActive(false);
        stopListRepository.save(stopList);
    }

    @Override
    @Transactional
    public StopListView deactivateStopList(Long id) {
        if (id == null) {
            throw new BadRequestException("StopList ID cannot be null");
        }

        StopList stopList = stopListRepository.findByIdOrThrow(id);

        if (!stopList.isActive()) {
            throw new BadRequestException("StopList is already inactive.");
        }

        stopList.setActive(false);
        StopList updatedStopList = stopListRepository.save(stopList);

        return stopListViewMapper.toView(updatedStopList);
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
