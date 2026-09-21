package controller;

import dao.reservation.ReservationDAO;
import model.entity.Client;
import model.entity.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Represents the ReservationValidator component used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationValidator {

    /**
     * Validates the data required to create a reservation.
     *
     * <p>The validation checks the selected vehicle, client,
     * reservation dates, vehicle service status, and vehicle
     * availability for the requested period.</p>
     *
     * @param vehicle vehicle selected for the reservation
     * @param client client creating the reservation
     * @param from reservation start date
     * @param to reservation end date
     * @throws IllegalArgumentException if the supplied data is invalid
     *                                  or the vehicle is currently in service
     * @throws SQLException if vehicle availability cannot be checked
     */
    public void validate(
            Vehicle vehicle,
            Client client,
            LocalDate from,
            LocalDate to) throws SQLException {

        if (vehicle == null) {
            throw new IllegalArgumentException("Vozilo nije odabrano.");
        }

        if (client == null) {
            throw new IllegalArgumentException("Korisnik nije prijavljen.");
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("Morate odabrati datume.");
        }

        if ("Servis".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalArgumentException(
                    "Vozilo je trenutno na servisu i nije dostupno za rezervaciju."
            );
        }

        if (from.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Datum početka ne može biti u prošlosti."
            );
        }

        if (!to.isAfter(from)) {
            throw new IllegalArgumentException(
                    "Datum završetka mora biti nakon datuma početka."
            );
        }

        if (!ReservationDAO.isVehicleAvailable(
                vehicle.getId(), from, to)) {

            throw new IllegalArgumentException(
                    "Vozilo nije dostupno za odabrane datume."
            );
        }
    }
}