package service;

import dao.AdminDAO;
import util.PasswordUtil;

/**
 * Provides application services for adminpassword operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AdminPasswordService {

    public enum Result {
        EMPTY_FIELDS,
        PASSWORD_TOO_SHORT,
        PASSWORDS_NOT_MATCHING,
        ADMIN_NOT_FOUND,
        INVALID_CURRENT_PASSWORD,
        SAVE_ERROR,
        SUCCESS
    }

    private AdminPasswordService() {
    }

    /**
     * Performs the changepassword operation.
     *
     * @param currentPassword supplied value used by this operation
     * @param newPassword supplied value used by this operation
     * @param confirmPassword supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Result changePassword(
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
        if (currentPassword.isBlank()
                || newPassword.isBlank()
                || confirmPassword.isBlank()) {
            return Result.EMPTY_FIELDS;
        }

        if (newPassword.length() < 6) {
            return Result.PASSWORD_TOO_SHORT;
        }

        if (!newPassword.equals(confirmPassword)) {
            return Result.PASSWORDS_NOT_MATCHING;
        }

        String currentHash =
                AdminDAO.getPasswordHash("admin");

        if (currentHash == null) {
            return Result.ADMIN_NOT_FOUND;
        }

        if (!PasswordUtil.verifyPassword(
                currentPassword,
                currentHash
        )) {
            return Result.INVALID_CURRENT_PASSWORD;
        }

        String newPasswordHash =
                PasswordUtil.hashPassword(newPassword);

        boolean success =
                AdminDAO.updatePassword(
                        "admin",
                        newPasswordHash
                );

        return success
                ? Result.SUCCESS
                : Result.SAVE_ERROR;
    }
}
