package java15.projectrestaurant.controller;

import jakarta.validation.Valid;
import java15.projectrestaurant.dto.request.ChequeAverageSumByRestaurantIdRequest;
import java15.projectrestaurant.dto.request.ChequeRequest;
import java15.projectrestaurant.dto.request.ChequeSumByWaiterRequest;
import java15.projectrestaurant.dto.response.ChequeAverageSumByRestaurantIdView;
import java15.projectrestaurant.dto.response.ChequeSumByWaiterView;
import java15.projectrestaurant.dto.response.ChequeView;
import java15.projectrestaurant.dto.response.SimpleResponseMessage;
import java15.projectrestaurant.service.ChequeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/cheques")
@RequiredArgsConstructor
public class ChequeController {
    private final ChequeService chequeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @PostMapping
    public ResponseEntity<ChequeView> createCheque(
            @Valid @RequestBody ChequeRequest request) {
        return ResponseEntity.ok(chequeService.createCheque(request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    @GetMapping("/getChequeSumByWaiter")
    public ChequeSumByWaiterView getChequeSumByWaiter(
            @Valid @RequestBody(required = false) ChequeSumByWaiterRequest request) {
        return chequeService.getChequeSumByWaiter(request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ChequeView> updateCheque(
            @PathVariable Long id,
            @Valid @RequestBody ChequeRequest request) {
        return ResponseEntity.ok(chequeService.updateCheque(id, request));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponseMessage deleteCheque(@PathVariable Long id) {
        chequeService.deleteCheque(id);
        return new SimpleResponseMessage("Cheque successfully deleted");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getChequeAverageSumByRestaurantId/{restaurantId}")
    public ChequeAverageSumByRestaurantIdView getChequeAverageSumByRestaurantId(
            @PathVariable Long restaurantId,
            @RequestBody(required = false) ChequeAverageSumByRestaurantIdRequest request) {
        return chequeService.getChequeAverageSumByRestaurantId(restaurantId, request);
    }
}
