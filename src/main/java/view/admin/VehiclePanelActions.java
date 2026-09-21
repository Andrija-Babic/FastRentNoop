package view.admin;

import controller.VehicleController;
import model.entity.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Provides user-interface actions related to vehiclepanel.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class VehiclePanelActions {

    private VehiclePanelActions() {
    }

    /**
     * Adds the supplied object to the managed collection or component.
     *
     * @param panel supplied value used by this operation
    */
    public static void addVehicle(VehiclesPanel panel) {
        JFrame parent =
                (JFrame) SwingUtilities.getWindowAncestor(panel);

        new AddVehicleDialog(parent).setVisible(true);

        panel.refreshTable();
    }

    /**
     * Performs the editvehicle operation.
     *
     * @param panel supplied value used by this operation
     * @param table supplied value used by this operation
    */
    public static void editVehicle(
            VehiclesPanel panel,
            JTable table
    ) {
        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Odaberite vozilo za uređivanje.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        int vehicleId =
                (int) table.getModel()
                        .getValueAt(modelRow, 0);

        Vehicle vehicle =
                findVehicleById(vehicleId);

        if (vehicle == null) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Vozilo nije pronađeno.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JFrame parent =
                (JFrame) SwingUtilities
                        .getWindowAncestor(panel);

        new EditVehicleDialog(
                parent,
                vehicle
        ).setVisible(true);

        panel.refreshTable();
    }

    /**
     * Deletes the vehicle data.
     *
     * @param panel supplied value used by this operation
     * @param table supplied value used by this operation
    */
    public static void deleteVehicle(
            VehiclesPanel panel,
            JTable table
    ) {
        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    panel,
                    "Odaberite vozilo za brisanje.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        int vehicleId =
                (int) table.getModel()
                        .getValueAt(modelRow, 0);

        int confirmation =
                JOptionPane.showConfirmDialog(
                        panel,
                        "Jeste li sigurni da želite "
                                + "izbrisati ovo vozilo?",
                        "Potvrda brisanja",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        VehicleController.deleteVehicle(
                vehicleId
        );

        panel.refreshTable();

        JOptionPane.showMessageDialog(
                panel,
                "Vozilo je uspješno izbrisano.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static Vehicle findVehicleById(int id) {
        List<Vehicle> vehicles =
                VehicleController.getAllVehicles();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }

        return null;
    }
}
