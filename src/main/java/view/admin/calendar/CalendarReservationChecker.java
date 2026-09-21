package view.admin.calendar;

import model.entity.Reservation;
import model.entity.Vehicle;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides checks related to calendarreservation data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class CalendarReservationChecker {

    private CalendarReservationChecker() {
    }

    /**
     * Returns whether vehiclereserved.
     *
     * @param vehicle supplied value used by this operation
     * @param date supplied value used by this operation
     * @param reservations supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean isVehicleReserved(
            Vehicle vehicle,
            LocalDate date,
            List<Reservation> reservations
    ) {
        for (Reservation reservation : reservations) {

            if (reservation.getVehicle().getId() != vehicle.getId()) {
                continue;
            }

            String status = reservation.getStatus();

            if ("Otkazano".equalsIgnoreCase(status)) {
                continue;
            }

            if ("Izvršeno".equalsIgnoreCase(status)
                    || "Završeno".equalsIgnoreCase(status)) {
                continue;
            }

            boolean active =
                    !date.isBefore(reservation.getFrom())
                            && date.isBefore(reservation.getTo());

            if (active) {
                return true;
            }
        }

        return false;
    }
}
