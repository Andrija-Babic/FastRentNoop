package view.admin;

import controller.LoginController;
import view.login.LoginFrame;

import javax.swing.*;

/**
 * Provides navigation and application menu actions for the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class MenuBar extends JMenuBar {

    private final AdminMainFrame frame;

    /**
     * Creates a new MenuBar instance.
     *
     * @param frame supplied value used by this operation
    */
    public MenuBar(AdminMainFrame frame) {
        this.frame = frame;

        add(createNavigationMenu());
        add(createAccountMenu());
        add(createApplicationMenu());
    }

    private JMenu createNavigationMenu() {

        JMenu menu = new JMenu("Navigacija");

        JMenuItem reservations =
                new JMenuItem("Rezervacije");

        reservations.addActionListener(
                e -> frame.showPanel("RESERVATIONS")
        );

        JMenuItem vehicles =
                new JMenuItem("Vozila");

        vehicles.addActionListener(
                e -> frame.showPanel("VEHICLES")
        );

        JMenuItem clients =
                new JMenuItem("Klijenti");

        clients.addActionListener(
                e -> frame.showPanel("CLIENTS")
        );

        JMenuItem calendar =
                new JMenuItem("Kalendar");

        calendar.addActionListener(
                e -> frame.showPanel("CALENDAR")
        );

        menu.add(reservations);
        menu.add(vehicles);
        menu.add(clients);
        menu.add(calendar);

        return menu;
    }

    private JMenu createAccountMenu() {

        JMenu menu = new JMenu("Račun");

        JMenuItem password =
                new JMenuItem("Promijeni lozinku");

        password.addActionListener(e -> {

            ChangeAdminPasswordDialog dialog =
                    new ChangeAdminPasswordDialog(frame);

            dialog.setVisible(true);
        });

        JMenuItem logout =
                new JMenuItem("Odjava");

        logout.addActionListener(e -> {

            LoginController.logout();

            frame.dispose();

            new LoginFrame().setVisible(true);
        });

        menu.add(password);
        menu.add(logout);

        return menu;
    }

    private JMenu createApplicationMenu() {

        JMenu menu = new JMenu("Aplikacija");

        JMenuItem exit =
                new JMenuItem("Izlaz");

        exit.addActionListener(
                e -> System.exit(0)
        );

        menu.add(exit);

        return menu;
    }
}
