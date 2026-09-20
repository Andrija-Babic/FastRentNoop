package controller;

import dao.reservation.ReservationDAO;
import model.entity.Client;
import model.entity.Reservation;
import model.entity.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Coordinates application operations related to reservation.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationController {

    private static final ReservationValidator validator = new ReservationValidator();
    private static final ReservationPricingService pricingService =
            new ReservationPricingService();

    /**
     * Creates or stores the data handled by this method.
     *
     * @param vehicle supplied value used by this operation
     * @param client supplied value used by this operation
     * @param from supplied value used by this operation
     * @param to supplied value used by this operation
    */
    public static void createReservation(
            Vehicle vehicle,
            Client client,
            LocalDate from,
            LocalDate to) throws SQLException {

        validator.validate(vehicle, client, from, to);

        double total = pricingService.calculatePrice(
                vehicle,
                client,
                from,
                to
        );

        Reservation reservation = new Reservation(
                0,
                vehicle,
                client,
                from,
                to,
                total,
                "Potvrđeno"
        );

        ReservationDAO.insert(reservation);
    }

    /**
     * Updates the reservationstatus data.
     *
     * @param reservation supplied value used by this operation
     * @param newStatus supplied value used by this operation
    */
    public static void updateReservationStatus(
            Reservation reservation,
            String newStatus) throws SQLException {

        if (reservation == null) {
            throw new IllegalArgumentException("Rezervacija nije odabrana.");
        }

        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Status nije odabran.");
        }

        ReservationDAO.updateStatus(reservation.getId(), newStatus);
    }
}
