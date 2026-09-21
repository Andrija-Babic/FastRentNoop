package model.strategy;

/**
 * Defines the available reservation pricing strategy types.
 *
 * <p>This enum is used to select the appropriate
 * {@link PriceStrategy} implementation.</p>
 */
public enum PricingType {
    REGULAR,
    PREMIUM,
    STUDENT
}