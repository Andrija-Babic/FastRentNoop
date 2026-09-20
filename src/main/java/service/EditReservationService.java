package service;

import controller.ReservationController;
import model.entity.Reservation;

import java.sql.SQLException;

/**
 * Provides application services for editreservation operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditReservationService {

    public static class Result {

        private final boolean success;
        private final boolean validationError;
        private final String message;

        private Result(
                boolean success,
                boolean validationError,
                String message
        ) {
            this.success = success;
            this.validationError = validationError;
            this.message = message;
        }

        public static Result success() {
            return new Result(true, false, null);
        }

        public static Result validationError(
                String message
        ) {
            return new Result(
                    false,
                    true,
                    message
            );
        }

        public static Result databaseError() {
            return new Result(
                    false,
                    false,
                    null
            );
        }

        public boolean isSuccess() {
            return success;
        }

        public boolean isValidationError() {
            return validationError;
        }

        public String getMessage() {
            return message;
        }
    }

    private EditReservationService() {
    }

    /**
     * Updates the status data.
     *
     * @param reservation supplied value used by this operation
     * @param newStatus supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Result updateStatus(
            Reservation reservation,
            String newStatus
    ) {
        if (newStatus == null || newStatus.isBlank()) {
            return Result.validationError(
                    "Status rezervacije nije ispravan."
            );
        }

        try {
            ReservationController.updateReservationStatus(
                    reservation,
                    newStatus
            );

            return Result.success();

        } catch (IllegalArgumentException e) {
            return Result.validationError(
                    e.getMessage()
            );

        } catch (SQLException e) {
            e.printStackTrace();

            return Result.databaseError();
        }
    }
}
