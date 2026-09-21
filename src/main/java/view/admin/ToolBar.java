package view.admin;

import javax.swing.*;
import java.awt.*;

/**
 * Provides context-sensitive toolbar actions for the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ToolBar extends JToolBar {

    private final AdminMainFrame frame;

    /**
     * Creates a new ToolBar instance.
     *
     * @param frame supplied value used by this operation
    */
    public ToolBar(AdminMainFrame frame) {
        this.frame = frame;

        setFloatable(false);
        setBackground(Color.WHITE);
    }

    /**
     * Shows the requested user-interface content.
     *
     * @param panelName supplied value used by this operation
    */
    public void showForPanel(String panelName) {

        removeAll();

        switch (panelName) {

            case "VEHICLES" ->
                    createVehicleActions();

            case "CLIENTS" ->
                    createClientActions();

            case "RESERVATIONS" ->
                    createReservationActions();

            case "CALENDAR" ->
                    createCalendarActions();
        }

        revalidate();
        repaint();
    }

    private void createVehicleActions() {

        JButton addButton =
                new JButton("Dodaj vozilo");

        addButton.addActionListener(
                e -> VehiclePanelActions.addVehicle(
                        frame.getVehiclesPanel()
                )
        );

        JButton editButton =
                new JButton("Uredi vozilo");

        editButton.addActionListener(
                e -> VehiclePanelActions.editVehicle(
                        frame.getVehiclesPanel(),
                        frame.getVehiclesPanel().getTable()
                )
        );

        JButton deleteButton =
                new JButton("Izbriši vozilo");

        deleteButton.addActionListener(
                e -> VehiclePanelActions.deleteVehicle(
                        frame.getVehiclesPanel(),
                        frame.getVehiclesPanel().getTable()
                )
        );

        add(addButton);
        add(editButton);
        add(deleteButton);
    }

    private void createClientActions() {

        JButton editButton =
                new JButton("Uredi klijenta");

        editButton.addActionListener(
                e -> frame.editSelectedClient()
        );

        JButton deleteButton =
                new JButton("Obriši klijenta");

        deleteButton.addActionListener(
                e -> frame.deleteSelectedClient()
        );

        add(editButton);
        add(deleteButton);
    }

    private void createReservationActions() {

        JButton editButton =
                new JButton("Uredi rezervaciju");

        editButton.addActionListener(
                e -> frame.editSelectedReservation()
        );

        add(editButton);
    }

    private void createCalendarActions() {
        // Kalendar trenutno nema posebne akcije.
    }
}
