package model.strategy;

/**
 * Calculates reservation prices according to the regular-client pricing rule.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class RegularStrategy implements PriceStrategy {

    @Override
    public double calculate(double pricePerDay, long days) {
        return pricePerDay * days;
    }
}
