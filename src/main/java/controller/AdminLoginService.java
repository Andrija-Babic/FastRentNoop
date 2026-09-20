package controller;

import dao.AdminDAO;
import util.PasswordUtil;

/**
 * Provides application services for adminlogin operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AdminLoginService {

    /**
     * Authenticates the supplied account information.
     *
     * @param username supplied value used by this operation
     * @param password supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public boolean login(
            String username,
            String password) {

        String passwordHash =
                AdminDAO.getPasswordHash(
                        username
                );

        if (passwordHash == null) {
            return false;
        }

        return PasswordUtil.verifyPassword(
                password,
                passwordHash
        );
    }
}
