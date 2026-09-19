package dao.reservation;

import database.DBConnection;
import model.entity.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides data-access operations for reservationstatus data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationStatusDAO {

    /**
     * Updates the expiredreservations data.
    */
    public static void updateExpiredReservations()
            throws SQLException {

        String selectSql = """
            SELECT id
            FROM reservations
            WHERE to_date <= CURRENT_DATE
              AND status = 'Potvrđeno'
        """;

        List<Integer> expiredIds = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(selectSql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                expiredIds.add(rs.getInt("id"));
            }
        }

        if (expiredIds.isEmpty()) {
            return;
        }

        String updateSql = """
            UPDATE reservations
            SET status = 'Izvršeno'
            WHERE to_date <= CURRENT_DATE
              AND status = 'Potvrđeno'
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(updateSql)) {

            ps.executeUpdate();
        }

        for (Integer id : expiredIds) {

            Reservation reservation =
                    ReservationRegistry.get(id);

            if (reservation != null) {
                reservation.setStatus("Izvršeno");
            }
        }
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

        String sql = """
            UPDATE reservations
            SET status = ?
            WHERE id = ?
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, reservationId);

            ps.executeUpdate();
        }

        Reservation reservation =
                ReservationRegistry.get(reservationId);

        if (reservation != null) {
            reservation.setStatus(status);
        }
    }
}
