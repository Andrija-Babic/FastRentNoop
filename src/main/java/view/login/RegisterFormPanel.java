package view.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing panel used for registerform operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class RegisterFormPanel extends JPanel {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    /**
     * Creates a new RegisterFormPanel instance.
     *
     * @param parent supplied value used by this operation
    */
    public RegisterFormPanel(RegisterFrame parent) {
        setLayout(
                new BoxLayout(
                        this,
                        BoxLayout.Y_AXIS
                )
        );

        setBorder(
                new EmptyBorder(
                        30,
                        40,
                        20,
                        40
                )
        );

        setBackground(Color.WHITE);

        JLabel title =
                new JLabel(
                        "Registracija korisnika"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        add(title);

        add(
                Box.createRigidArea(
                        new Dimension(0, 25)
                )
        );

        firstNameField =
                addTextField(
                        "Ime:"
                );

        lastNameField =
                addTextField(
                        "Prezime:"
                );

        usernameField =
                addTextField(
                        "Korisničko ime:"
                );

        passwordField =
                addPasswordField(
                        "Lozinka:"
                );

        confirmPasswordField =
                addPasswordField(
                        "Ponovi lozinku:"
                );
    }

    private JTextField addTextField(
            String labelText
    ) {
        JLabel label =
                new JLabel(labelText);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        add(label);

        add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        JTextField field =
                new JTextField();

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        add(field);

        add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );

        return field;
    }

    private JPasswordField addPasswordField(
            String labelText
    ) {
        JLabel label =
                new JLabel(labelText);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        add(label);

        add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );

        JPasswordField field =
                new JPasswordField();

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        field.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        add(field);

        add(
                Box.createRigidArea(
                        new Dimension(0, 12)
                )
        );

        return field;
    }

    /**
     * Returns the firstname.
     *
     * @return the value produced by this operation
    */
    public String getFirstName() {
        return firstNameField
                .getText()
                .trim();
    }

    /**
     * Returns the lastname.
     *
     * @return the value produced by this operation
    */
    public String getLastName() {
        return lastNameField
                .getText()
                .trim();
    }

    /**
     * Returns the username.
     *
     * @return the value produced by this operation
    */
    public String getUsername() {
        return usernameField
                .getText()
                .trim();
    }

    /**
     * Returns the password.
     *
     * @return the value produced by this operation
    */
    public String getPassword() {
        return new String(
                passwordField.getPassword()
        );
    }

    /**
     * Returns the confirmpassword.
     *
     * @return the value produced by this operation
    */
    public String getConfirmPassword() {
        return new String(
                confirmPasswordField.getPassword()
        );
    }
}
