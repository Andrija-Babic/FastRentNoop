package controller;

import dao.reservation.ReservationDAO;
import model.entity.Client;
import model.entity.Reservation;
import model.entity.Vehicle;

/**
 * Coordinates application operations related to reservation.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationController {

    private static final ReservationValidator validator =
            new ReservationValidator();

    private static final ReservationPricingService pricingService =
            new ReservationPricingService();

    /**
     * Creates a new reservation and returns the created reservation.
     *
     * @param vehicle vehicle used for the reservation
     * @param client client making the reservation
     * @param from start date of the reservation
     * @param to end date of the reservation
     *
     * @return created reservation
     *
     * @throws IllegalArgumentException if the reservation data is invalid
     * @throws java.sql.SQLException if the reservation cannot be stored
     */
    public static Reservation createReservation(
            Vehicle vehicle,
            Client client,
            java.time.LocalDate from,
            java.time.LocalDate to) throws java.sql.SQLException {

        validator.validate(
                vehicle,
                client,
                from,
                to
        );

        double total =
                pricingService.calculatePrice(
                        vehicle,
                        client,
                        from,
                        to
                );

        Reservation reservation =
                new Reservation(
                        0,
                        vehicle,
                        client,
                        from,
                        to,
                        total,
                        "Potvrđeno"
                );

        ReservationDAO.insert(reservation);

        return reservation;
    }

    /**
     * Updates the status of an existing reservation.
     *
     * @param reservation reservation whose status is being updated
     * @param newStatus new reservation status
     *
     * @throws java.sql.SQLException if the status cannot be stored
     * @throws IllegalArgumentException if the reservation or status is invalid
     */
    public static void updateReservationStatus(
            Reservation reservation,
            String newStatus) throws java.sql.SQLException {

        if (reservation == null) {
            throw new IllegalArgumentException(
                    "Rezervacija nije odabrana."
            );
        }

        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException(
                    "Status nije odabran."
            );
        }

        ReservationDAO.updateStatus(
                reservation.getId(),
                newStatus
        );
    }
}