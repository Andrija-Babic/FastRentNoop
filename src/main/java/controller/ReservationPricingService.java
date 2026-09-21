package controller;

import model.entity.Client;
import model.entity.Vehicle;
import model.strategy.PremiumStrategy;
import model.strategy.PriceStrategy;
import model.strategy.PricingType;
import model.strategy.RegularStrategy;
import model.strategy.StudentStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Provides application services for reservation pricing operations.
 *
 * <p>This class acts as the Context in the Strategy design pattern.
 * It selects the appropriate pricing strategy and delegates the
 * price calculation to that strategy.</p>
 *
 * <p>Concrete pricing strategies are stored in a registry, allowing
 * new strategies to be added without creating a large conditional
 * chain in the price calculation logic.</p>
 */
public class ReservationPricingService {

    private final Map<PricingType, PriceStrategy> strategies = Map.of(
            PricingType.REGULAR, new RegularStrategy(),
            PricingType.PREMIUM, new PremiumStrategy(),
            PricingType.STUDENT, new StudentStrategy()
    );

    /**
     * Calculates the total reservation price using the pricing strategy
     * associated with the client.
     *
     * @param vehicle vehicle used for the reservation
     * @param client client making the reservation
     * @param from start date of the reservation
     * @param to end date of the reservation
     *
     * @return calculated total reservation price
     */
    public double calculatePrice(
            Vehicle vehicle,
            Client client,
            LocalDate from,
            LocalDate to) {

        long days = ChronoUnit.DAYS.between(from, to);

        PriceStrategy strategy =
                strategies.get(client.getPricingType());

        return strategy.calculate(
                vehicle.getPricePerDay(),
                days
        );
    }
}