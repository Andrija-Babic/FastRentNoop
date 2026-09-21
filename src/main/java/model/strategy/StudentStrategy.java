package model.strategy;

/**
 * Calculates reservation prices according to the student-client pricing rule.
 *
 * <p>Student clients receive a 15% discount on the regular reservation price.</p>
 */
public class StudentStrategy implements PriceStrategy {

    @Override
    public double calculate(double pricePerDay, long days) {
        return pricePerDay * days * 0.85;
    }
}