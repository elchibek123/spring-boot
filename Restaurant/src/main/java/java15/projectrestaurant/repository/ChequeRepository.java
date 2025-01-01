package java15.projectrestaurant.repository;

import java15.projectrestaurant.exception.NotFoundException;
import java15.projectrestaurant.model.Cheque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("SELECT SUM(c.priceAverage) FROM Cheque c WHERE c.user.id = :waiterId AND FUNCTION('DATE', c.createdAt) = :date")
    BigDecimal sumByWaiterAndDate(@Param("waiterId") Long waiterId, @Param("date") LocalDate date);

    default Cheque findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Cheque with ID: " + id + " not found")
        );
    }

    @Query("""
    SELECT COALESCE(AVG(
        (SELECT SUM(mi.price) 
         FROM MenuItem mi 
         JOIN mi.cheques ch 
         WHERE ch.id = c.id)
    ), 0) 
    FROM Cheque c 
    WHERE c.user.restaurant.id = :restaurantId 
    AND DATE(c.createdAt) = :date
""")
    BigDecimal findDailyAverageSumByRestaurantIdAndDate(
            @Param("restaurantId") Long restaurantId,
            @Param("date") LocalDate date
    );
}
