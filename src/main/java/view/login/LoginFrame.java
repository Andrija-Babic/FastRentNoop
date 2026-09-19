package view.login;

import controller.LoginController;
import dao.client.ClientDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing window used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class LoginFrame extends JFrame {

    private final LoginFormPanel formPanel;

    /**
     * Creates a new LoginFrame instance.
    */
    public LoginFrame() {
        setTitle("FastRent - Prijava");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(
                new Color(245, 247, 250)
        );

        add(createTopBar(), BorderLayout.NORTH);

        formPanel = new LoginFormPanel(this);

        add(formPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBorder(
                new EmptyBorder(10, 20, 10, 20)
        );

        panel.setBackground(Color.WHITE);

        JButton exitBtn =
                new JButton("Izlaz");

        exitBtn.setPreferredSize(
                new Dimension(100, 35)
        );

        exitBtn.addActionListener(
                e -> System.exit(0)
        );

        panel.add(
                exitBtn,
                BorderLayout.EAST
        );

        return panel;
    }

    /**
     * Performs the main operation.
     *
     * @param args supplied value used by this operation
    */
    public static void main(String[] args) {

        /*
         * Make sure the database contains
         * username and password_hash columns.
         */
        ClientDAO.initializePasswordColumns();

        /*
         * Make sure the database contains
         * the admin account.
         */
        LoginController.initializeAdmin();

        SwingUtilities.invokeLater(
                () -> new LoginFrame().setVisible(true)
        );
    }
}
