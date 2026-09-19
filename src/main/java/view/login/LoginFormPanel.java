package view.login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing panel used for loginform operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class LoginFormPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Creates a new LoginFormPanel instance.
     *
     * @param parent supplied value used by this operation
    */
    public LoginFormPanel(JFrame parent) {
        setLayout(new GridBagLayout());
        setBackground(
                new Color(245, 247, 250)
        );

        add(createMainPanel(parent));
    }

    private JPanel createMainPanel(JFrame parent) {
        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        mainPanel.setBorder(
                new EmptyBorder(
                        40,
                        60,
                        40,
                        60
                )
        );

        JLabel carLabel =
                new JLabel("🚗");

        carLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        80
                )
        );

        carLabel.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        0,
                        40
                )
        );

        mainPanel.add(
                carLabel,
                BorderLayout.WEST
        );

        mainPanel.add(
                createFormPanel(parent),
                BorderLayout.CENTER
        );

        return mainPanel;
    }

    private JPanel createFormPanel(JFrame parent) {
        JPanel formPanel =
                new JPanel();

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        formPanel.setBackground(Color.WHITE);

        JLabel title =
                new JLabel("Prijava u FastRent");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        formPanel.add(title);

        formPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 30)
                )
        );

        formPanel.add(
                createInputRow(
                        "Korisničko ime:",
                        true
                )
        );

        formPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 20)
                )
        );

        formPanel.add(
                createInputRow(
                        "Lozinka:",
                        false
                )
        );

        formPanel.add(
                Box.createRigidArea(
                        new Dimension(0, 30)
                )
        );

        formPanel.add(
                createButtonPanel(parent)
        );

        return formPanel;
    }

    private JPanel createInputRow(
            String labelText,
            boolean isUsername
    ) {
        JPanel row =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                20,
                                0
                        )
                );

        row.setBackground(Color.WHITE);

        JLabel label =
                new JLabel(labelText);

        label.setPreferredSize(
                new Dimension(120, 30)
        );

        if (isUsername) {
            usernameField =
                    new JTextField(20);

            row.add(label);
            row.add(usernameField);
        } else {
            passwordField =
                    new JPasswordField(20);

            row.add(label);
            row.add(passwordField);
        }

        return row;
    }

    private JPanel createButtonPanel(JFrame parent) {
        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                20,
                                0
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton loginBtn =
                new JButton("Prijava");

        loginBtn.setPreferredSize(
                new Dimension(120, 35)
        );

        loginBtn.addActionListener(
                e -> LoginActions.login(
                        parent,
                        this
                )
        );

        JButton registerBtn =
                new JButton("Registracija");

        registerBtn.setPreferredSize(
                new Dimension(120, 35)
        );

        registerBtn.addActionListener(
                e -> LoginActions.openRegistration()
        );

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        return buttonPanel;
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
}}
