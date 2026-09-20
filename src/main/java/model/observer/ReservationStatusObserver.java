package model.observer;

import model.entity.Reservation;

import javax.swing.*;

/**
 * Observes reservation status changes and triggers a UI refresh action.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationStatusObserver
        implements Observer {

    private final Runnable updateAction;

    /**
     * Creates a new ReservationStatusObserver instance.
     *
     * @param updateAction supplied value used by this operation
    */
    public ReservationStatusObserver(
            Runnable updateAction) {

        this.updateAction = updateAction;
    }

    @Override
    public void update(Subject subject) {

        if (!(subject instanceof Reservation)) {
            return;
        }

        Reservation reservation =
                (Reservation) subject;

        System.out.println(
                "Status rezervacije #"
                        + reservation.getId()
                        + " promijenjen u: "
                        + reservation.getStatus()
        );

        if (updateAction != null) {

            SwingUtilities.invokeLater(
                    updateAction
            );
        }
    }
}
