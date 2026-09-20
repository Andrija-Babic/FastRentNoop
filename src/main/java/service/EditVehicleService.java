package service;

import controller.VehicleController;
import model.entity.Vehicle;

/**
 * Provides application services for editvehicle operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditVehicleService {

    public enum Result {
        INVALID_NUMBER,
        INVALID_NAME,
        ERROR,
        SUCCESS;

        private String message;

        public String getMessage() {
            return message;
        }

        private Result withMessage(String message) {
            this.message = message;
            return this;
        }
    }

    private EditVehicleService() {
    }

    /**
     * Updates the vehicle data.
     *
     * @param vehicle supplied value used by this operation
     * @param fullName supplied value used by this operation
     * @param yearText supplied value used by this operation
     * @param priceText supplied value used by this operation
     * @param status supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Result updateVehicle(
            Vehicle vehicle,
            String fullName,
            String yearText,
            String priceText,
            String status
    ) {
        if (fullName.isEmpty()) {
            return Result.ERROR.withMessage("");
        }

        String[] parts =
                fullName.split("\\s+", 2);

        String brand = parts[0];

        String model =
                parts.length > 1
                        ? parts[1]
                        : "";

        int year;
        double price;

        try {
            year = Integer.parseInt(yearText);
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            return Result.INVALID_NUMBER;
        }

        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setPricePerDay(price);
        vehicle.setStatus(status);

        try {
            VehicleController.updateVehicle(
                    vehicle,
                    vehicle.getFullName(),
                    vehicle.getYear(),
                    vehicle.getPricePerDay(),
                    vehicle.getStatus()
            );

            return Result.SUCCESS;

        } catch (Exception e) {
            return Result.ERROR.withMessage(
                    e.getMessage()
            );
        }
    }
}
