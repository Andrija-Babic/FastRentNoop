package dao.reservation;

import model.entity.Client;
import model.entity.Reservation;
import model.entity.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps database records to reservation domain objects.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ReservationMapper {

    /**
     * Performs the fromresultset operation.
     *
     * @param rs supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Reservation fromResultSet(
            ResultSet rs) throws SQLException {

        Vehicle vehicle = new Vehicle(
                rs.getInt("v_id"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getDouble("price"),
                rs.getString("v_status")
        );

        Client client = new Client(
                rs.getInt("c_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("premium") == 1
        );

        return new Reservation(
                rs.getInt("id"),
                vehicle,
                client,
                rs.getDate("from_date").toLocalDate(),
                rs.getDate("to_date").toLocalDate(),
                rs.getDouble("total_price"),
                rs.getString("status")
        );
    }
}
