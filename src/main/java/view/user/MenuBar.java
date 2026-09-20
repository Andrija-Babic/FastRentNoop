package view.user;

import controller.LoginController;
import view.login.LoginFrame;

import javax.swing.*;

/**
 * Provides navigation and application menu actions for the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class MenuBar extends JMenuBar {

    private final UserMainFrame frame;

    /**
     * Creates a new MenuBar instance.
     *
     * @param frame supplied value used by this operation
    */
    public MenuBar(UserMainFrame frame) {
        this.frame = frame;

        add(createNavigationMenu());
        add(createAccountMenu());
        add(createApplicationMenu());
    }

    private JMenu createNavigationMenu() {

        JMenu menu = new JMenu("Navigacija");

        JMenuItem reservations =
                new JMenuItem("Moje rezervacije");

        reservations.addActionListener(
                e -> frame.showPanel("RESERVATIONS")
        );

        JMenuItem vehicles =
                new JMenuItem("Ponuda vozila");

        vehicles.addActionListener(
                e -> frame.showPanel("VEHICLES")
        );

        JMenuItem calendar =
                new JMenuItem("Kalendar");

        calendar.addActionListener(
                e -> frame.showPanel("CALENDAR")
        );

        menu.add(reservations);
        menu.add(vehicles);
        menu.add(calendar);

        return menu;
    }

    private JMenu createAccountMenu() {

        JMenu menu = new JMenu("Račun");

        JMenuItem logout =
                new JMenuItem("Odjava");

        logout.addActionListener(e -> {

            LoginController.logout();

            frame.dispose();

            new LoginFrame().setVisible(true);
        });

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
