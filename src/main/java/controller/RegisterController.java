package controller;

import dao.client.ClientDAO;
import model.entity.Client;
import model.strategy.PricingType;
import util.PasswordUtil;

/**
 * Coordinates application operations related to client registration.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class RegisterController {

    private RegisterController() {
    }

    /**
     * Represents the result of a registration attempt.
     */
    public enum Result {
        EMPTY_FIRST_NAME,
        EMPTY_LAST_NAME,
        EMPTY_USERNAME,
        EMPTY_PASSWORD,
        EMPTY_CONFIRM_PASSWORD,
        ADMIN_USERNAME,
        PASSWORD_TOO_SHORT,
        PASSWORD_MISMATCH,
        USERNAME_TAKEN,
        REGISTRATION_ERROR,
        SUCCESS
    }

    /**
     * Registers a new regular client.
     *
     * @param firstName client's first name
     * @param lastName client's last name
     * @param username client's username
     * @param password client's password
     * @param confirmPassword repeated password used for confirmation
     * @return registration result
     */
    public static Result register(
            String firstName,
            String lastName,
            String username,
            String password,
            String confirmPassword) {

        if (firstName.isBlank()) {
            return Result.EMPTY_FIRST_NAME;
        }

        if (lastName.isBlank()) {
            return Result.EMPTY_LAST_NAME;
        }

        if (username.isBlank()) {
            return Result.EMPTY_USERNAME;
        }

        if (password.isBlank()) {
            return Result.EMPTY_PASSWORD;
        }

        if (confirmPassword.isBlank()) {
            return Result.EMPTY_CONFIRM_PASSWORD;
        }

        if (username.equalsIgnoreCase("admin")) {
            return Result.ADMIN_USERNAME;
        }

        if (password.length() < 6) {
            return Result.PASSWORD_TOO_SHORT;
        }

        if (!password.equals(confirmPassword)) {
            return Result.PASSWORD_MISMATCH;
        }

        if (ClientDAO.findByUsername(username) != null) {
            return Result.USERNAME_TAKEN;
        }

        Client client = new Client(
                0,
                firstName,
                lastName,
                false
        );

        client.setPricingType(PricingType.REGULAR);

        String passwordHash =
                PasswordUtil.hashPassword(password);

        boolean success =
                ClientDAO.insertWithCredentials(
                        client,
                        username,
                        passwordHash
                );

        if (!success) {
            return Result.REGISTRATION_ERROR;
        }

        return Result.SUCCESS;
    }
}