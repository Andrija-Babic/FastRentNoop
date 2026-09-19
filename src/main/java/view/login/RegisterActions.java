package view.login;

import dao.client.ClientDAO;
import model.entity.Client;
import util.PasswordUtil;

import javax.swing.*;

/**
 * Provides user-interface actions related to register.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class RegisterActions {

    private RegisterActions() {
    }

    /**
     * Performs the register operation.
     *
     * @param parent supplied value used by this operation
     * @param form supplied value used by this operation
    */
    public static void register(
            RegisterFrame parent,
            RegisterFormPanel form
    ) {
        String firstName =
                form.getFirstName();

        String lastName =
                form.getLastName();

        String username =
                form.getUsername();

        String password =
                form.getPassword();

        String confirmPassword =
                form.getConfirmPassword();

        if (firstName.isBlank()
                || lastName.isBlank()
                || username.isBlank()
                || password.isBlank()
                || confirmPassword.isBlank()) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Molimo ispunite sva polja.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (username.equalsIgnoreCase("admin")) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Korisničko ime 'admin' nije dostupno.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Lozinka mora imati najmanje 6 znakova.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Lozinke se ne podudaraju.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (ClientDAO.findByUsername(username)
                != null) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Korisničko ime je već zauzeto.",
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean premium = false;

        Client client =
                new Client(
                        0,
                        firstName,
                        lastName,
                        premium
                );

        String passwordHash =
                PasswordUtil.hashPassword(
                        password
                );

        boolean success =
                ClientDAO.insertWithCredentials(
                        client,
                        username,
                        passwordHash
                );

        if (!success) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Greška prilikom registracije korisnika.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                parent,
                "Registracija je uspješna!",
                "Uspjeh",
                JOptionPane.INFORMATION_MESSAGE
        );

        parent.dispose();
    }
}
