package model.strategy;

/**
 * Defines the strategy contract used to calculate reservation prices.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public interface PriceStrategy {
    double calculate(double pricePerDay, long days);
}
