package model.observer;

/**
 * Defines the contract for objects that can register and notify observers.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public interface Subject {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
