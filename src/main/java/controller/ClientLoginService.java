package controller;

import dao.client.ClientDAO;
import model.entity.Client;
import util.PasswordUtil;

/**
 * Provides application services for clientlogin operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientLoginService {

    /**
     * Authenticates the supplied account information.
     *
     * @param username supplied value used by this operation
     * @param password supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public Client login(
            String username,
            String password) {

        Client client =
                ClientDAO.findByUsername(
                        username
                );

        if (client == null) {
            return null;
        }

        String passwordHash =
                client.getPasswordHash();

        if (passwordHash == null) {
            return null;
        }

        if (!PasswordUtil.verifyPassword(
                password,
                passwordHash)) {

            return null;
        }

        return client;
    }
}
