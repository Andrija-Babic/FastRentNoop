package dao.vehicle;

import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Provides data-access operations for vehicle status data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleStatusDAO {

    /**
     * Updates the current vehicle statuses based on active reservations.
     *
     * <p>Vehicles marked as {@code Servis} are intentionally excluded
     * from automatic status changes and therefore remain in service status.</p>
     */
    public static void updateCurrentVehicleStatuses() {

        String sql = """
            UPDATE vehicles v
            SET status =
                CASE
                    WHEN v.status = 'Servis'
                    THEN 'Servis'

                    WHEN EXISTS (
                        SELECT 1
                        FROM reservations r
                        WHERE r.vehicle_id = v.id
                          AND r.status = 'Potvrđeno'
                          AND r.from_date <= CURRENT_DATE
                          AND r.to_date > CURRENT_DATE
                    )
                    THEN 'Iznajmljeno'

                    ELSE 'Dostupno'
                END
            WHERE v.status <> 'Servis'
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}