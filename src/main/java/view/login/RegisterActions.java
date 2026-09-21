package view.login;

import controller.RegisterController;

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
        RegisterController.Result result =
                RegisterController.register(
                        form.getFirstName(),
                        form.getLastName(),
                        form.getUsername(),
                        form.getPassword(),
                        form.getConfirmPassword()
                );

        switch (result) {

            case EMPTY_FIRST_NAME:
            case EMPTY_LAST_NAME:
            case EMPTY_USERNAME:
            case EMPTY_PASSWORD:
            case EMPTY_CONFIRM_PASSWORD:

                JOptionPane.showMessageDialog(
                        parent,
                        "Molimo ispunite sva polja.",
                        "Greška",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            case ADMIN_USERNAME:

                JOptionPane.showMessageDialog(
                        parent,
                        "Korisničko ime 'admin' nije dostupno.",
                        "Greška",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            case PASSWORD_TOO_SHORT:

                JOptionPane.showMessageDialog(
                        parent,
                        "Lozinka mora imati najmanje 6 znakova.",
                        "Greška",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            case PASSWORD_MISMATCH:

                JOptionPane.showMessageDialog(
                        parent,
                        "Lozinke se ne podudaraju.",
                        "Greška",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            case USERNAME_TAKEN:

                JOptionPane.showMessageDialog(
                        parent,
                        "Korisničko ime je već zauzeto.",
                        "Greška",
                        JOptionPane.WARNING_MESSAGE
                );
                return;

            case REGISTRATION_ERROR:

                JOptionPane.showMessageDialog(
                        parent,
                        "Greška prilikom registracije korisnika.",
                        "Greška",
                        JOptionPane.ERROR_MESSAGE
                );
                return;

            case SUCCESS:

                JOptionPane.showMessageDialog(
                        parent,
                        "Registracija je uspješna!",
                        "Uspjeh",
                        JOptionPane.INFORMATION_MESSAGE
                );

                parent.dispose();
                return;
        }
    }
}