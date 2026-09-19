package dao.reservation;

import database.DBConnection;
import model.entity.Reservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides data-access operations for reservationquery data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationQueryDAO {

    private static final String BASE_QUERY = """
        SELECT
            r.id, r.from_date, r.to_date, r.total_price, r.status,
            v.id AS v_id, v.brand, v.model, v.year,
            v.price, v.status AS v_status,
            c.id AS c_id, c.first_name, c.last_name, c.premium
        FROM reservations r
        JOIN vehicles v ON r.vehicle_id = v.id
        JOIN clients c ON r.client_id = c.id
        """;

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Reservation> findAll()
            throws SQLException {

        return findReservations(
                BASE_QUERY + "ORDER BY r.id DESC",
                null
        );
    }

    /**
     * Finds and returns matching byclient data.
     *
     * @param clientId supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static List<Reservation> findByClient(
            int clientId) throws SQLException {

        String sql =
                BASE_QUERY +
                        "WHERE r.client_id = ? " +
                        "ORDER BY r.id DESC";

        return findReservations(sql, clientId);
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
            LocalDate from,
            LocalDate to) throws SQLException {

        String sql = """
            SELECT COUNT(*)
            FROM reservations
            WHERE vehicle_id = ?
              AND status = 'Potvrđeno'
              AND from_date < ?
              AND to_date > ?
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, vehicleId);
            ps.setDate(2, Date.valueOf(to));
            ps.setDate(3, Date.valueOf(from));

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) == 0;
            }
        }
    }

    private static List<Reservation> findReservations(
            String sql,
            Integer clientId) throws SQLException {

        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            if (clientId != null) {
                ps.setInt(1, clientId);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    reservations.add(
                            getRegisteredReservation(rs)
                    );
                }
            }
        }

        return reservations;
    }

    private static Reservation getRegisteredReservation(
            ResultSet rs) throws SQLException {

        Reservation reservation = ReservationMapper.fromResultSet(rs);

        Reservation existing =
                ReservationRegistry.get(
                        reservation.getId()
                );

        if (existing != null) {

            if (!existing.getStatus().equals(reservation.getStatus())) {

                existing.setStatus(
                        reservation.getStatus()
                );
            }

            return existing;
        }

        ReservationRegistry.put(
                reservation.getId(),
                reservation
        );

        return reservation;
    }
}
