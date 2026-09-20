package service;

import dao.client.ClientDAO;
import model.entity.Client;
import util.PasswordUtil;

/**
 * Provides application services for editclient operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditClientService {

    public enum Result {
        EMPTY_FIRST_NAME,
        EMPTY_LAST_NAME,
        ADMIN_USERNAME,
        PASSWORD_TOO_SHORT,
        USERNAME_TAKEN,
        UPDATE_ERROR,
        CREDENTIALS_ERROR,
        SUCCESS
    }

    private EditClientService() {
    }

    /**
     * Updates the client data.
     *
     * @param client supplied value used by this operation
     * @param firstName supplied value used by this operation
     * @param lastName supplied value used by this operation
     * @param username supplied value used by this operation
     * @param password supplied value used by this operation
     * @param premium supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static Result updateClient(
            Client client,
            String firstName,
            String lastName,
            String username,
            String password,
            boolean premium
    ) {
        if (firstName.isEmpty()) {
            return Result.EMPTY_FIRST_NAME;
        }

        if (lastName.isEmpty()) {
            return Result.EMPTY_LAST_NAME;
        }

        if (!username.isEmpty()
                && username.equalsIgnoreCase("admin")) {
            return Result.ADMIN_USERNAME;
        }

        if (!password.isEmpty()
                && password.length() < 6) {
            return Result.PASSWORD_TOO_SHORT;
        }

        if (!username.isEmpty()) {
            Client existing =
                    ClientDAO.findByUsername(username);

            if (existing != null
                    && existing.getId() != client.getId()) {
                return Result.USERNAME_TAKEN;
            }
        }

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPremium(premium);

        if (!ClientDAO.update(client)) {
            return Result.UPDATE_ERROR;
        }

        if (username.isEmpty()
                && password.isEmpty()) {
            client.setUsername(client.getUsername());
            return Result.SUCCESS;
        }

        String passwordHash = null;

        if (!password.isEmpty()) {
            passwordHash =
                    PasswordUtil.hashPassword(password);
        }

        boolean credentialsUpdated;

        if (passwordHash != null) {
            credentialsUpdated =
                    ClientDAO.setCredentials(
                            client.getId(),
                            username,
                            passwordHash
                    );

            client.setPasswordHash(passwordHash);
        } else {
            credentialsUpdated =
                    ClientDAO.setUsername(
                            client.getId(),
                            username
                    );
        }

        if (!credentialsUpdated) {
            return Result.CREDENTIALS_ERROR;
        }

        client.setUsername(username);

        return Result.SUCCESS;
    }
}
