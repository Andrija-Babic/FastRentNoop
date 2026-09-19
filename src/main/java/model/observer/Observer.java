package model.observer;


/**
 * Defines the observer contract used to react to subject state changes.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public interface Observer {

    void update(Subject subject);
}
