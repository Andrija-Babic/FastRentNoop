package controller;

import model.entity.Client;
import model.entity.Vehicle;
import model.strategy.PremiumStrategy;
import model.strategy.PriceStrategy;
import model.strategy.RegularStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Provides application services for reservationpricing operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationPricingService {

    /**
     * Calculates and returns the requested value.
     *
     * @param vehicle supplied value used by this operation
     * @param client supplied value used by this operation
     * @param from supplied value used by this operation
     * @param to supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public double calculatePrice(
            Vehicle vehicle,
            Client client,
            LocalDate from,
            LocalDate to) {

        long days = ChronoUnit.DAYS.between(from, to);

        PriceStrategy strategy;

        if (client.isPremium()) {
            strategy = new PremiumStrategy();
        } else {
            strategy = new RegularStrategy();
        }

        return strategy.calculate(
                vehicle.getPricePerDay(),
                days
        );
    }
}
