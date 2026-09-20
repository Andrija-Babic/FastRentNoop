package model.observer;

import model.entity.Vehicle;

/**
 * Observes vehicle status changes and triggers a UI refresh action.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleStatusObserver implements Observer {

    private final Runnable updateAction;

    /**
     * Creates a new VehicleStatusObserver instance.
     *
     * @param updateAction supplied value used by this operation
    */
    public VehicleStatusObserver(Runnable updateAction) {
        this.updateAction = updateAction;
    }

    @Override
    public void update(Subject subject) {

        if (subject instanceof Vehicle) {
            Vehicle vehicle = (Vehicle) subject;

            System.out.println(
                    "Status vozila " +
                            vehicle.getFullName() +
                            " promijenjen u: " +
                            vehicle.getStatus()
            );

            if (updateAction != null) {
                updateAction.run();
            }
        }
    }
}
