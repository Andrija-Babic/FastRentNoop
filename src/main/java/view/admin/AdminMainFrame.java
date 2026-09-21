package view.admin;

import view.admin.calendar.CalendarPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Provides the main Swing window for the FastRent administrator.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AdminMainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private MainContentPanel reservationsPanel;
    private ClientsPanel clientsPanel;
    private VehiclesPanel vehiclesPanel;
    private CalendarPanel calendarPanel;

    private ToolBar toolBar;

    /**
     * Creates a new AdminMainFrame instance.
     */
    public AdminMainFrame() {

        setTitle("FastRent - Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =================================================
        // CONTENT
        // =================================================

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        calendarPanel = new CalendarPanel();

        reservationsPanel =
                new MainContentPanel(
                        this::refreshCalendar
                );

        clientsPanel =
                new ClientsPanel();

        vehiclesPanel =
                new VehiclesPanel();

        contentPanel.add(
                reservationsPanel,
                "RESERVATIONS"
        );

        contentPanel.add(
                clientsPanel,
                "CLIENTS"
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

        // =================================================
        // TOP AREA
        // =================================================

        JPanel topArea =
                new JPanel(
                        new BorderLayout()
                );

        topArea.setBackground(
                Color.WHITE
        );

        // FastRent header
        topArea.add(
                new TopBarPanel(),
                BorderLayout.NORTH
        );

        // Menu bar
        MenuBar menuBar =
                new MenuBar(this);

        topArea.add(
                menuBar,
                BorderLayout.CENTER
        );

        // =================================================
        // TOOL BAR
        // =================================================

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

        // =================================================
        // INITIAL PANEL
        // =================================================

        showPanel("RESERVATIONS");
    }

    // =====================================================
    // SHOW PANEL
    // =====================================================

    /**
     * Shows the requested user-interface content.
     *
     * @param name supplied value used by this operation
     */
    public void showPanel(String name) {

        if ("RESERVATIONS".equals(name)) {
            reservationsPanel.refreshTable();
        }

        if ("CALENDAR".equals(name)) {
            calendarPanel.refreshTable();
        }

        cardLayout.show(
                contentPanel,
                name
        );

        toolBar.showForPanel(name);
    }

    /**
     * Refreshes the administrator calendar.
     */
    public void refreshCalendar() {
        calendarPanel.refreshTable();
    }

    // =====================================================
    // GETTERS
    // =====================================================

    /**
     * Returns the vehiclespanel.
     *
     * @return the value produced by this operation
     */
    public VehiclesPanel getVehiclesPanel() {
        return vehiclesPanel;
    }

    // =====================================================
    // CLIENT ACTIONS
    // =====================================================

    /**
     * Performs the editselectedclient operation.
     */
    public void editSelectedClient() {

        ClientPanelActions.editSelectedClient(
                clientsPanel,
                clientsPanel.getTable(),
                clientsPanel.getModel(),
                clientsPanel::refreshTable
        );
    }

    /**
     * Deletes the selectedclient data.
     */
    public void deleteSelectedClient() {

        ClientPanelActions.deleteSelectedClient(
                clientsPanel,
                clientsPanel.getTable(),
                clientsPanel.getModel(),
                clientsPanel::refreshTable
        );
    }

    // =====================================================
    // RESERVATION ACTIONS
    // =====================================================

    /**
     * Performs the editselectedreservation operation.
     */
    public void editSelectedReservation() {

        reservationsPanel.editSelectedReservation();
    }
}