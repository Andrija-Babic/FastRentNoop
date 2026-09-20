package controller;

import dao.vehicle.VehicleDAO;
import model.entity.Vehicle;

import java.util.List;

/**
 * Coordinates application operations related to vehicle.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleController {

    /**
     * Returns the allvehicles.
     *
     * @return the value produced by this operation
    */
    public static List<Vehicle> getAllVehicles() {
        return VehicleDAO.findAll();
    }

    /**
     * Adds the supplied object to the managed collection or component.
     *
     * @param brandModel supplied value used by this operation
     * @param year supplied value used by this operation
     * @param price supplied value used by this operation
     * @param status supplied value used by this operation
    */
    public static void addVehicle(String brandModel,
                                  int year,
                                  double price,
                                  String status) {

        String[] parts = brandModel.split(" ");

        String brand = parts[0];
        String model = parts.length > 1 ? parts[1] : "";

        Vehicle vehicle = new Vehicle(
                0,
                brand,
                model,
                year,
                price,
                status
        );

        VehicleDAO.insert(vehicle);
    }

    /**
     * Updates the vehicle data.
     *
     * @param vehicle supplied value used by this operation
     * @param brandModel supplied value used by this operation
     * @param year supplied value used by this operation
     * @param price supplied value used by this operation
     * @param status supplied value used by this operation
    */
    public static void updateVehicle(Vehicle vehicle,
                                     String brandModel,
                                     int year,
                                     double price,
                                     String status) {

        String[] parts = brandModel.split("\\s+", 2);

        String brand = parts[0];
        String model = parts.length > 1 ? parts[1] : "";

        vehicle.setBrand(parts[0]);
        vehicle.setModel(parts.length > 1 ? parts[1] : "");
        vehicle.setYear(year);
        vehicle.setPricePerDay(price);
        vehicle.setStatus(status);

        VehicleDAO.update(vehicle);
    }

    /**
     * Deletes the vehicle data.
     *
     * @param id supplied value used by this operation
    */
    public static void deleteVehicle(int id) {
        VehicleDAO.delete(id);
    }
}
