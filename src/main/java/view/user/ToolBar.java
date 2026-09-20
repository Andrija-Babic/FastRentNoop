package view.user;

import javax.swing.*;
import java.awt.*;

/**
 * Provides context-sensitive toolbar actions for the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ToolBar extends JToolBar {

    private final UserMainFrame frame;

    /**
     * Creates a new ToolBar instance.
     *
     * @param frame supplied value used by this operation
    */
    public ToolBar(UserMainFrame frame) {

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

            case "RESERVATIONS" ->
                    createReservationActions();

            case "VEHICLES" ->
                    createVehicleActions();

        }

        revalidate();
        repaint();
    }

    // =====================================================
    // RESERVATION ACTIONS
    // =====================================================

    private void createReservationActions() {

        JButton showButton =
                new JButton(
                        "Prikaži rezervaciju"
                );

        showButton.addActionListener(
                e -> frame
                        .getReservationsPanel()
                        .showSelectedReservation()
        );

        JButton cancelButton =
                new JButton(
                        "Otkaži rezervaciju"
                );

        cancelButton.addActionListener(
                e -> frame
                        .getReservationsPanel()
                        .cancelSelectedReservation()
        );

        add(showButton);
        add(cancelButton);
    }

    // =====================================================
    // VEHICLE ACTIONS
    // =====================================================

    private void createVehicleActions() {

        JButton createReservationButton =
                new JButton(
                        "Kreiraj rezervaciju"
                );

        createReservationButton.addActionListener(
                e -> VehicleReservationActions.createReservation(
                        frame.getVehiclesPanel(),
                        frame.getVehiclesPanel().getTable(),
                        frame.getVehiclesPanel().getModel()
                )
        );

        add(createReservationButton);
    }

}
