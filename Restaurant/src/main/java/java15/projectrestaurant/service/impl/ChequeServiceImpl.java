package java15.projectrestaurant.service.impl;

import java15.projectrestaurant.dto.request.ChequeAverageSumByRestaurantIdRequest;
import java15.projectrestaurant.dto.request.ChequeRequest;
import java15.projectrestaurant.dto.request.ChequeSumByWaiterRequest;
import java15.projectrestaurant.dto.response.ChequeAverageSumByRestaurantIdView;
import java15.projectrestaurant.dto.response.ChequeSumByWaiterView;
import java15.projectrestaurant.dto.response.ChequeView;
import java15.projectrestaurant.exception.BadRequestException;
import java15.projectrestaurant.mapper.ChequeAverageSumByRestaurantIdViewMapper;
import java15.projectrestaurant.mapper.ChequeViewMapper;
import java15.projectrestaurant.mapper.ChequeSumByWaiterViewMapper;
import java15.projectrestaurant.model.Cheque;
import java15.projectrestaurant.model.MenuItem;
import java15.projectrestaurant.model.Restaurant;
import java15.projectrestaurant.model.User;
import java15.projectrestaurant.repository.ChequeRepository;
import java15.projectrestaurant.repository.MenuItemRepository;
import java15.projectrestaurant.repository.RestaurantRepository;
import java15.projectrestaurant.repository.UserRepository;
import java15.projectrestaurant.service.ChequeService;
import java15.projectrestaurant.util.DateUtils;
import java15.projectrestaurant.util.SecurityUtils;
import java15.projectrestaurant.validation.validator.GenericRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final ChequeViewMapper chequeViewMapper;
    private final UserRepository userRepository;
    private final ChequeSumByWaiterViewMapper chequeSumByWaiterViewMapper;
    private final ChequeAverageSumByRestaurantIdViewMapper chequeAverageSumByRestaurantIdViewMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    @Transactional
    public ChequeView createCheque(ChequeRequest request) {
        GenericRequestValidator.validateField(request, request.menuItemIds(), "MenuItem IDs");

        List<MenuItem> menuItems = menuItemRepository.findAllById(request.menuItemIds());
        if (menuItems.size() != request.menuItemIds().size()) {
            throw new BadRequestException("One or more MenuItem IDs are invalid.");
        }

        List<MenuItem> stopListedItems = menuItems.stream()
                .filter(MenuItem::isStopListed)
                .toList();

        if (!stopListedItems.isEmpty()) {
            String stopListedItemNames = stopListedItems.stream()
                    .map(item -> String.format("'%s'", item.getName()))
                    .collect(Collectors.joining(", "));

            throw new BadRequestException(
                    String.format("Cannot create cheque. The following items are currently in StopList: %s",
                            stopListedItemNames)
            );
        }

        String username = SecurityUtils.getCurrentUser();
        User user = userRepository.findByEmailOrThrow(username);

        Cheque cheque = new Cheque();
        cheque.setUser(user);
        cheque.setMenuItems(new ArrayList<>(menuItems));

        menuItems.forEach(menuItem -> {
            if (menuItem.getCheques() == null) {
                menuItem.setCheques(new ArrayList<>());
            }
            menuItem.getCheques().add(cheque);
        });

        BigDecimal priceAverage = calculateAveragePrice(menuItems);
        cheque.setPriceAverage(priceAverage);

        Cheque savedCheque = chequeRepository.save(cheque);
        return chequeViewMapper.toView(savedCheque);
    }

    @Override
    public ChequeSumByWaiterView getChequeSumByWaiter(ChequeSumByWaiterRequest request) {
        LocalDate date = DateUtils.resolveDate(request, ChequeSumByWaiterRequest::date);

        String username = SecurityUtils.getCurrentUser();

        User user = userRepository.findByEmailOrThrow(username);

        BigDecimal chequeSum = chequeRepository.sumByWaiterAndDate(user.getId(), date);

        return chequeSumByWaiterViewMapper.toView(user, chequeSum);
    }

    @Override
    @Transactional
    public ChequeView updateCheque(Long id, ChequeRequest request) {
        GenericRequestValidator.validateField(request, id, "ID");

        List<MenuItem> menuItems = menuItemRepository.findAllById(request.menuItemIds());
        if (menuItems.size() != request.menuItemIds().size()) {
            throw new BadRequestException("One or more MenuItem IDs are invalid.");
        }

        List<MenuItem> stopListedItems = menuItems.stream()
                .filter(MenuItem::isStopListed)
                .toList();

        if (!stopListedItems.isEmpty()) {
            String stopListedItemNames = stopListedItems.stream()
                    .map(item -> String.format("'%s'", item.getName()))
                    .collect(Collectors.joining(", "));

            throw new BadRequestException(
                    String.format("Cannot update cheque. The following items are currently in StopList: %s",
                            stopListedItemNames)
            );
        }

        Cheque cheque = chequeRepository.findByIdOrThrow(id);

        if (cheque.getMenuItems() != null) {
            cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
        }

        cheque.setMenuItems(new ArrayList<>(menuItems));
        menuItems.forEach(menuItem -> {
            if (menuItem.getCheques() == null) {
                menuItem.setCheques(new ArrayList<>());
            }
            menuItem.getCheques().add(cheque);
        });

        BigDecimal newPriceAverage = calculateAveragePrice(menuItems);
        cheque.setPriceAverage(newPriceAverage);

        Cheque savedCheque = chequeRepository.save(cheque);
        return chequeViewMapper.toView(savedCheque);
    }

    @Override
    @Transactional
    public void deleteCheque(Long id) {
        if (id == null) {
            throw new BadRequestException("Cheque ID cannot be null");
        }

        Cheque cheque = chequeRepository.findByIdOrThrow(id);

        if (cheque.getMenuItems() != null) {
            cheque.getMenuItems().forEach(menuItem -> {
                menuItem.getCheques().remove(cheque);
                menuItem.setCheques(
                        menuItem.getCheques().stream()
                                .filter(c -> !c.getId().equals(cheque.getId()))
                                .collect(Collectors.toList())
                );
            });
            cheque.setMenuItems(new ArrayList<>());
        }

        User user = cheque.getUser();
        if (user != null) {
            user.getCheques().remove(cheque);
            user.setCheques(
                    user.getCheques().stream()
                            .filter(c -> !c.getId().equals(cheque.getId()))
                            .collect(Collectors.toList())
            );
            cheque.setUser(null);
        }

        chequeRepository.delete(cheque);
    }

    @Override
    public ChequeAverageSumByRestaurantIdView getChequeAverageSumByRestaurantId(
            Long restaurantId,
            ChequeAverageSumByRestaurantIdRequest request) {
        LocalDate date = DateUtils.resolveDate(request, ChequeAverageSumByRestaurantIdRequest::date);

        Restaurant restaurant = restaurantRepository.findByIdOrThrow(restaurantId);

        BigDecimal averageSum = chequeRepository.findDailyAverageSumByRestaurantIdAndDate(restaurantId, date);

        return chequeAverageSumByRestaurantIdViewMapper.toView(restaurant, averageSum);
    }

    private BigDecimal calculateAveragePrice(List<MenuItem> menuItems) {
        if (menuItems == null || menuItems.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalPrice = menuItems.stream()
                .map(MenuItem::getPrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPrice.divide(
                BigDecimal.valueOf(menuItems.size()),
                2,
                RoundingMode.HALF_UP
        );
    }
}
