package model.entity;

import model.observer.Observer;
import model.observer.Subject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vehicle reservation made by a FastRent client.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class Reservation implements Subject {

    private int id;
    private Vehicle vehicle;
    private Client client;
    private LocalDate from;
    private LocalDate to;
    private double totalPrice;
    private String status;

    private final List<Observer> observers =
            new ArrayList<>();

    /**
     * Creates a new Reservation instance.
     *
     * @param id supplied value used by this operation
     * @param vehicle supplied value used by this operation
     * @param client supplied value used by this operation
     * @param from supplied value used by this operation
     * @param to supplied value used by this operation
     * @param totalPrice supplied value used by this operation
     * @param status supplied value used by this operation
    */
    public Reservation(
            int id,
            Vehicle vehicle,
            Client client,
            LocalDate from,
            LocalDate to,
            double totalPrice,
            String status) {

        this.id = id;
        this.vehicle = vehicle;
        this.client = client;
        this.from = from;
        this.to = to;
        this.totalPrice = totalPrice;
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
     * Sets the id.
     *
     * @param id supplied value used by this operation
    */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the vehicle.
     *
     * @return the value produced by this operation
    */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns the client.
     *
     * @return the value produced by this operation
    */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the from.
     *
     * @return the value produced by this operation
    */
    public LocalDate getFrom() {
        return from;
    }

    /**
     * Returns the to.
     *
     * @return the value produced by this operation
    */
    public LocalDate getTo() {
        return to;
    }

    /**
     * Returns the totalprice.
     *
     * @return the value produced by this operation
    */
    public double getTotalPrice() {
        return totalPrice;
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

        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {

        if (observer != null
                && !observers.contains(observer)) {

            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {

        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {

        List<Observer> snapshot =
                new ArrayList<>(observers);

        for (Observer observer : snapshot) {
            observer.update(this);
        }
    }
}
