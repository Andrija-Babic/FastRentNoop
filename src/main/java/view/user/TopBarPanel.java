package view.user;

import controller.LoginController;
import model.entity.Client;
import view.login.LoginFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the top header area of the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class TopBarPanel extends JPanel {

    /**
     * Creates a new TopBarPanel instance.
    */
    public TopBarPanel() {

        setLayout(new BorderLayout());

        setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        setBackground(Color.WHITE);

        JLabel logo =
                new JLabel("🚗 FastRent");

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        // =================================================
        // USER STATUS
        // =================================================

        Client client =
                LoginController.getLoggedInClient();

        JPanel userPanel =
                new JPanel();

        userPanel.setLayout(
                new BoxLayout(
                        userPanel,
                        BoxLayout.Y_AXIS
                )
        );

        userPanel.setBackground(Color.WHITE);

        if (client != null) {

            JLabel nameLabel =
                    new JLabel(
                            client.getFullName()
                    );

            nameLabel.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            14
                    )
            );

            JLabel statusLabel =
                    new JLabel(
                            client.isPremium()
                                    ? "Premium korisnik"
                                    : "Standardni korisnik"
                    );

            statusLabel.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            12
                    )
            );

            nameLabel.setAlignmentX(
                    Component.RIGHT_ALIGNMENT
            );

            statusLabel.setAlignmentX(
                    Component.RIGHT_ALIGNMENT
            );

            userPanel.add(nameLabel);
            userPanel.add(statusLabel);
        }

        // =================================================
        // LOGOUT
        // =================================================

        JButton logoutBtn =
                new JButton("Izlaz");

        logoutBtn.setPreferredSize(
                new Dimension(
                        100,
                        35
                )
        );

        logoutBtn.addActionListener(e -> {

            LoginController.logout();

            Window window =
                    SwingUtilities.getWindowAncestor(
                            this
                    );

            if (window != null) {
                window.dispose();
            }

            new LoginFrame().setVisible(true);
        });

        JPanel rightPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        rightPanel.setBackground(Color.WHITE);

        rightPanel.add(userPanel);
        rightPanel.add(logoutBtn);

        add(
                logo,
                BorderLayout.WEST
        );

        add(
                rightPanel,
                BorderLayout.EAST
        );
    }
}
