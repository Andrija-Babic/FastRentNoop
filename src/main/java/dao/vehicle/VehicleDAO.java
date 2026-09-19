package dao.vehicle;

import database.DBConnection;
import model.entity.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides data-access operations for vehicle data.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleDAO {

    /**
     * Creates or stores the data handled by this method.
     *
     * @param vehicle supplied value used by this operation
    */
    public static void insert(Vehicle vehicle) {
        String sql =
                "INSERT INTO vehicles(brand, model, year, price, status) " +
                        "VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setString(5, vehicle.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the  data.
     *
     * @param vehicle supplied value used by this operation
    */
    public static void update(Vehicle vehicle) {
        String sql = """
            UPDATE vehicles
            SET brand=?, model=?, year=?, price=?, status=?
            WHERE id=?
        """;

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicle.getBrand());
            ps.setString(2, vehicle.getModel());
            ps.setInt(3, vehicle.getYear());
            ps.setDouble(4, vehicle.getPricePerDay());
            ps.setString(5, vehicle.getStatus());
            ps.setInt(6, vehicle.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the  data.
     *
     * @param id supplied value used by this operation
    */
    public static void delete(int id) {
        String sql = "DELETE FROM vehicles WHERE id=?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds and returns matching all data.
     *
     * @return the value produced by this operation
    */
    public static List<Vehicle> findAll() {
        return VehicleQueryDAO.findAll();
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

        return VehicleQueryDAO.isVehicleAvailable(
                vehicleId, from, to);
    }

    /**
     * Updates the currentvehiclestatuses data.
    */
    public static void updateCurrentVehicleStatuses() {
        VehicleStatusDAO.updateCurrentVehicleStatuses();
    }
}
