package model.entity;

import model.observer.Observer;
import model.observer.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a vehicle available through the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class Vehicle implements Subject {

    private int id;
    private String brand;
    private String model;
    private int year;
    private double pricePerDay;
    private String status;

    /*
     * Observers are shared by vehicle ID.
     *
     * This allows different Vehicle objects representing
     * the same database vehicle to notify the same observers.
     *
     * A Set is used so the same Observer cannot be registered
     * more than once for the same vehicle ID.
     */
    private static final Map<Integer, Set<Observer>>
            observerRegistry =
            new ConcurrentHashMap<>();

    /**
     * Creates a new Vehicle instance.
     *
     * @param id supplied value used by this operation
     * @param brand supplied value used by this operation
     * @param model supplied value used by this operation
     * @param year supplied value used by this operation
     * @param pricePerDay supplied value used by this operation
     * @param status supplied value used by this operation
    */
    public Vehicle(int id, String brand, String model, int year, double pricePerDay, String status) {

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    /**
     * Returns the id.
     *
     * @return the value produced by this operation
    */
    public int getId() {
        return id;
    }

    /**
     * Returns the brand.
     *
     * @return the value produced by this operation
    */
    public String getBrand() {
        return brand;
    }

    /**
     * Returns the model.
     *
     * @return the value produced by this operation
    */
    public String getModel() {
        return model;
    }

    /**
     * Returns the year.
     *
     * @return the value produced by this operation
    */
    public int getYear() {
        return year;
    }

    /**
     * Returns the priceperday.
     *
     * @return the value produced by this operation
    */
    public double getPricePerDay() {
        return pricePerDay;
    }

    /**
     * Returns the status.
     *
     * @return the value produced by this operation
    */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the brand.
     *
     * @param brand supplied value used by this operation
    */
    public void setBrand(String brand) {
        this.brand = brand;
        notifyObservers();
    }

    /**
     * Sets the model.
     *
     * @param model supplied value used by this operation
    */
    public void setModel(String model) {
        this.model = model;
        notifyObservers();
    }

    /**
     * Sets the year.
     *
     * @param year supplied value used by this operation
    */
    public void setYear(int year) {
        this.year = year;
        notifyObservers();
    }

    /**
     * Sets the priceperday.
     *
     * @param pricePerDay supplied value used by this operation
    */
    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
        notifyObservers();
    }

    /**
     * Sets the status.
     *
     * @param status supplied value used by this operation
    */
    public void setStatus(String status) {

        if (this.status != null
                && this.status.equals(status)) {

            return;
        }

        this.status = status;

        /*
         * Notify all observers registered for this
         * vehicle ID.
         */
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {

        if (observer == null) {
            return;
        }

        observerRegistry.computeIfAbsent(
                        id,
                        key -> ConcurrentHashMap.newKeySet()
                )
                .add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        if (observer == null) {
            return;
        }

        Set<Observer> observers = observerRegistry.get(id);

        if (observers == null) {
            return;
        }

        observers.remove(observer);

        /*
         * Remove the empty observer set so the registry
         * does not unnecessarily keep unused entries.
         */
        if (observers.isEmpty()) {
            observerRegistry.remove(id);
        }
    }

    @Override
    public void notifyObservers() {

        Set<Observer> observers =
                observerRegistry.get(id);

        if (observers == null) {
            return;
        }

        /*
         * Create a snapshot so changes to the observer
         * collection during notification do not interfere
         * with the current notification cycle.
         */
        List<Observer> snapshot = new ArrayList<>(observers);

        for (Observer observer : snapshot) {
            observer.update(this);
        }
    }

    /**
     * Returns the fullname.
     *
     * @return the value produced by this operation
    */
    public String getFullName() {
        return brand + " " + model;
    }
}
