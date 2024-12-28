package java15.projectrestaurant.service;

import java15.projectrestaurant.dto.request.ChequeAverageSumByRestaurantIdRequest;
import java15.projectrestaurant.dto.request.ChequeRequest;
import java15.projectrestaurant.dto.request.ChequeSumByWaiterRequest;
import java15.projectrestaurant.dto.response.ChequeAverageSumByRestaurantIdView;
import java15.projectrestaurant.dto.response.ChequeSumByWaiterView;
import java15.projectrestaurant.dto.response.ChequeView;

public interface ChequeService {
    ChequeView createCheque(ChequeRequest request);

    ChequeSumByWaiterView getChequeSumByWaiter(ChequeSumByWaiterRequest request);

    ChequeView updateCheque(Long id, ChequeRequest request);

    void deleteCheque(Long id);

    ChequeAverageSumByRestaurantIdView getChequeAverageSumByRestaurantId(Long restaurantId, ChequeAverageSumByRestaurantIdRequest request);
}
