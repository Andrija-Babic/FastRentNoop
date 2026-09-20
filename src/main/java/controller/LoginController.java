package controller;

import model.entity.Client;

/**
 * Coordinates application operations related to login.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class LoginController {

    private static Client loggedInClient;
    private static String role;

    private static final AdminLoginService adminLoginService =
            new AdminLoginService();

    private static final ClientLoginService clientLoginService =
            new ClientLoginService();

    // =====================================================
    // INITIALIZE ADMIN
    // =====================================================

    /**
     * Initializes the data or component required by the application.
    */
    public static void initializeAdmin() {

        AdminInitializer.initialize();
    }

    // =====================================================
    // LOGIN
    // =====================================================

    /**
     * Authenticates the supplied account information.
     *
     * @param username supplied value used by this operation
     * @param password supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static boolean login(
            String username,
            String password) {

        if (username == null
                || username.isBlank()
                || password == null
                || password.isBlank()) {

            return false;
        }

        username = username.trim();

        if (username.equalsIgnoreCase("admin")) {

            if (!adminLoginService.login(
                    username,
                    password)) {

                return false;
            }

            role = "ADMIN";
            loggedInClient = null;

            return true;
        }

        Client client =
                clientLoginService.login(
                        username,
                        password
                );

        if (client == null) {
            return false;
        }

        loggedInClient = client;
        role = "USER";

        return true;
    }

    // =====================================================
    // LOGGED-IN CLIENT
    // =====================================================

    /**
     * Returns the loggedinclient.
     *
     * @return the value produced by this operation
    */
    public static Client getLoggedInClient() {

        return loggedInClient;
    }

    // =====================================================
    // ROLE
    // =====================================================

    /**
     * Returns the role.
     *
     * @return the value produced by this operation
    */
    public static String getRole() {

        return role;
    }

    // =====================================================
    // LOGOUT
    // =====================================================

    /**
     * Logs the current user out of the application.
    */
    public static void logout() {

        loggedInClient = null;
        role = null;
    }
}
