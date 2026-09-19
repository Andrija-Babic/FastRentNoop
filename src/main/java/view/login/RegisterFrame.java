package view.login;

import javax.swing.*;
import java.awt.*;

/**
 * Provides the Swing window used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class RegisterFrame extends JFrame {

    private final RegisterFormPanel formPanel;

    /**
     * Creates a new RegisterFrame instance.
    */
    public RegisterFrame() {
        setTitle("FastRent - Registracija");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                WindowConstants.DISPOSE_ON_CLOSE
        );

        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(245, 247, 250)
        );

        formPanel = new RegisterFormPanel(this);

        add(
                formPanel,
                BorderLayout.CENTER
        );

        add(
                createButtonPanel(),
                BorderLayout.SOUTH
        );
    }

    private JPanel createButtonPanel() {
        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        panel.setBackground(Color.WHITE);

        JButton cancelButton =
                new JButton("Odustani");

        cancelButton.setPreferredSize(
                new Dimension(120, 35)
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        JButton registerButton =
                new JButton("Registriraj se");

        registerButton.setPreferredSize(
                new Dimension(140, 35)
        );

        registerButton.addActionListener(
                e -> RegisterActions.register(
                        this,
                        formPanel
                )
        );

        panel.add(cancelButton);
        panel.add(registerButton);

        return panel;
    }
}
