package view.user.calendar;

import controller.LoginController;
import dao.reservation.ReservationDAO;
import model.entity.Client;
import model.entity.Reservation;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides helper operations related to usercalendarreservation functionality.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class UserCalendarReservationHelper {

    private UserCalendarReservationHelper() {
    }

    /**
     * Loads the required data.
     *
     * @param parent supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static List<Reservation> loadActiveReservations(
            Component parent
    ) {

        Client client = LoginController.getLoggedInClient();

        if (client == null) {
            return List.of();
        }

        try {

            ReservationDAO.updateExpiredReservations();

            List<Reservation> reservations =
                    ReservationDAO.findByClient(
                            client.getId()
                    );

            return reservations.stream().filter(r ->
                            "Potvrđeno".equalsIgnoreCase(
                                    r.getStatus()
                            )
                    )
                    .toList();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    parent,
                    "Greška prilikom učitavanja kalendara.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return List.of();
        }
    }

    /**
     * Returns whether datereserved.
     *
     * @param reservation supplied value used by this operation
     * @param date supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean isDateReserved(
            Reservation reservation,
            LocalDate date
    ) {

        /*
         * Reservation occupies:
         *
         * from <= date < to
         */
        return !date.isBefore(reservation.getFrom()) && date.isBefore(
                reservation.getTo()
        );
    }
}
