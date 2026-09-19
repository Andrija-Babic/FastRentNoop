package view.login;

import controller.LoginController;
import view.admin.AdminMainFrame;
import view.user.UserMainFrame;

import javax.swing.*;

/**
 * Provides user-interface actions related to login.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class LoginActions {

    private LoginActions() {
    }

    /**
     * Authenticates the supplied account information.
     *
     * @param parent supplied value used by this operation
     * @param formPanel supplied value used by this operation
    */
    public static void login(
            JFrame parent,
            LoginFormPanel formPanel
    ) {
        String username =
                formPanel.getUsername();

        String password =
                formPanel.getPassword();

        if (username.isBlank()
                || password.isBlank()) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Unesite korisničko ime i lozinku.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (LoginController.login(
                username,
                password
        )) {

            if (LoginController
                    .getRole()
                    .equals("ADMIN")) {

                new AdminMainFrame()
                        .setVisible(true);

            } else {

                new UserMainFrame()
                        .setVisible(true);
            }

            parent.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    parent,
                    "Neispravno korisničko ime ili lozinka!",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Performs the openregistration operation.
    */
    public static void openRegistration() {
        new RegisterFrame()
                .setVisible(true);
    }
}
