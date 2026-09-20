package view.user;

import view.user.calendar.UserCalendarPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Provides the main Swing window for a logged-in FastRent user.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class UserMainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private MainContentPanel reservationsPanel;
    private VehiclesPanel vehiclesPanel;
    private UserCalendarPanel calendarPanel;

    private ToolBar toolBar;

    /**
     * Creates a new UserMainFrame instance.
    */
    public UserMainFrame() {

        setTitle("FastRent - Korisnik");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =====================================================
        // CONTENT
        // =====================================================

        cardLayout = new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        reservationsPanel =
                new MainContentPanel();

        vehiclesPanel =
                new VehiclesPanel();

        calendarPanel =
                new UserCalendarPanel();

        contentPanel.add(
                reservationsPanel,
                "RESERVATIONS"
        );

        contentPanel.add(
                vehiclesPanel,
                "VEHICLES"
        );

        contentPanel.add(
                calendarPanel,
                "CALENDAR"
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // TOP AREA
        // =====================================================

        JPanel topArea =
                new JPanel(
                        new BorderLayout()
                );

        topArea.setBackground(Color.WHITE);

        /*
         * FastRent logo/header
         */
        topArea.add(
                new TopBarPanel(),
                BorderLayout.NORTH
        );

        /*
         * Menu bar below the header.
         */
        MenuBar menuBar =
                new MenuBar(this);

        topArea.add(
                menuBar,
                BorderLayout.CENTER
        );

        /*
         * Contextual toolbar below the menu.
         */
        toolBar =
                new ToolBar(this);

        JPanel completeTopArea =
                new JPanel(
                        new BorderLayout()
                );

        completeTopArea.setBackground(
                Color.WHITE
        );

        completeTopArea.add(
                topArea,
                BorderLayout.NORTH
        );

        completeTopArea.add(
                toolBar,
                BorderLayout.CENTER
        );

        add(
                completeTopArea,
                BorderLayout.NORTH
        );

        // =====================================================
        // INITIAL PANEL
        // =====================================================

        showPanel("RESERVATIONS");
    }

    // =====================================================
    // PANEL NAVIGATION
    // =====================================================

    /**
     * Shows the requested user-interface content.
     *
     * @param name supplied value used by this operation
    */
    public void showPanel(String name) {

        cardLayout.show(
                contentPanel,
                name
        );

        if (name.equals("RESERVATIONS")) {

            reservationsPanel.refreshTable();

        } else if (name.equals("VEHICLES")) {

            vehiclesPanel.refreshTable();

        } else if (name.equals("CALENDAR")) {

            calendarPanel.refreshTable();
        }

        toolBar.showForPanel(name);
    }

    // =====================================================
    // GETTERS
    // =====================================================

    /**
     * Returns the reservationspanel.
     *
     * @return the value produced by this operation
    */
    public MainContentPanel getReservationsPanel() {

        return reservationsPanel;
    }

    /**
     * Returns the vehiclespanel.
     *
     * @return the value produced by this operation
    */
    public VehiclesPanel getVehiclesPanel() {

        return vehiclesPanel;
    }

    /**
     * Returns the calendarpanel.
     *
     * @return the value produced by this operation
    */
    public UserCalendarPanel getCalendarPanel() {

        return calendarPanel;
    }

    // =====================================================
    // MAIN
    // =====================================================

    /**
     * Performs the main operation.
     *
     * @param args supplied value used by this operation
    */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    UserMainFrame frame =
                            new UserMainFrame();

                    frame.setVisible(true);
                }
        );
    }
}
