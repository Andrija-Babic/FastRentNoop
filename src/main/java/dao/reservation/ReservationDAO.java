package dao.reservation;

import database.DBConnection;
import model.entity.Reservation;

import java.sql.*;
import java.util.List;

/**
 * Provides data-access operations for reservation data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationDAO {

    /**
     * Creates or stores the data handled by this method.
     *
     * @param reservation supplied value used by this operation
    */
    public static void insert(Reservation reservation)
            throws SQLException {

        String sql = """
            INSERT INTO reservations
            (vehicle_id, client_id, from_date, to_date,
             total_price, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, reservation.getVehicle().getId());
            ps.setInt(2, reservation.getClient().getId());
            ps.setDate(3, Date.valueOf(reservation.getFrom()));
            ps.setDate(4, Date.valueOf(reservation.getTo()));
            ps.setDouble(5, reservation.getTotalPrice());
            ps.setString(6, reservation.getStatus());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    reservation.setId(id);
                    ReservationRegistry.put(id, reservation);
                }
            }
        }
    }

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Reservation> findAll()
            throws SQLException {
        return ReservationQueryDAO.findAll();
    }

    /**
     * Finds and returns matching byclient data.
     *
     * @param clientId supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static List<Reservation> findByClient(int clientId)
            throws SQLException {
        return ReservationQueryDAO.findByClient(clientId);
    }

    /**
     * Returns whether vehicleavailable.
     *
     * @param vehicleId supplied value used by this operation
     * @param from supplied value used by this operation
     * @param to supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean isVehicleAvailable(
            int vehicleId,
            java.time.LocalDate from,
            java.time.LocalDate to)
            throws SQLException {

        return ReservationQueryDAO.isVehicleAvailable(
                vehicleId, from, to);
    }

    /**
     * Updates the expiredreservations data.
    */
    public static void updateExpiredReservations()
            throws SQLException {
        ReservationStatusDAO.updateExpiredReservations();
    }

    /**
     * Updates the status data.
     *
     * @param reservationId supplied value used by this operation
     * @param status supplied value used by this operation
    */
    public static void updateStatus(
            int reservationId,
            String status) throws SQLException {

        ReservationStatusDAO.updateStatus(
                reservationId, status);
    }
}
