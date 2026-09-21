package view.admin;

import model.entity.Vehicle;
import service.EditVehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing dialog used for editvehicle operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditVehicleDialog extends JDialog {

    private JTextField brandField;
    private JTextField yearField;
    private JTextField priceField;
    private JComboBox<String> statusBox;

    /**
     * Creates a new EditVehicleDialog instance.
     *
     * @param parent supplied value used by this operation
     * @param vehicle supplied value used by this operation
    */
    public EditVehicleDialog(
            JFrame parent,
            Vehicle vehicle
    ) {
        super(parent, "Uredi vozilo", true);

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        add(createFormPanel(vehicle), BorderLayout.CENTER);
        add(createButtonPanel(vehicle), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel(Vehicle vehicle) {
        JPanel panel = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        panel.setBackground(Color.WHITE);

        brandField = createInput(
                panel,
                "Marka i model:",
                vehicle.getFullName()
        );

        yearField = createInput(
                panel,
                "Godina:",
                String.valueOf(vehicle.getYear())
        );

        priceField = createInput(
                panel,
                "Cijena po danu:",
                String.valueOf(vehicle.getPricePerDay())
        );

        panel.add(new JLabel("Status:"));

        statusBox = new JComboBox<>(
                new String[]{
                        "Dostupno",
                        "Iznajmljeno",
                        "Servis"
                }
        );

        statusBox.setSelectedItem(
                vehicle.getStatus()
        );

        statusBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        panel.add(statusBox);

        return panel;
    }

    private JTextField createInput(
            JPanel panel,
            String labelText,
            String value
    ) {
        JLabel label = new JLabel(labelText);

        JTextField field = new JTextField(value);

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        panel.add(label);
        panel.add(
                Box.createRigidArea(
                        new Dimension(0, 5)
                )
        );
        panel.add(field);
        panel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );

        return field;
    }

    private JPanel createButtonPanel(Vehicle vehicle) {
        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        panel.setBackground(Color.WHITE);

        JButton saveBtn = new JButton("Spremi");
        JButton cancelBtn = new JButton("Odustani");

        saveBtn.addActionListener(
                e -> saveVehicle(vehicle)
        );

        cancelBtn.addActionListener(
                e -> dispose()
        );

        panel.add(cancelBtn);
        panel.add(saveBtn);

        return panel;
    }

    private void saveVehicle(Vehicle vehicle) {
        String fullName =
                brandField.getText().trim();

        String yearText =
                yearField.getText().trim();

        String priceText =
                priceField.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        EditVehicleService.Result result =
                EditVehicleService.updateVehicle(
                        vehicle,
                        fullName,
                        yearText,
                        priceText,
                        status
                );

        if (result == EditVehicleService.Result.SUCCESS) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vozilo je uspješno ažurirano.",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            return;
        }

        if (result == EditVehicleService.Result.INVALID_NUMBER) {
            JOptionPane.showMessageDialog(
                    this,
                    "Godina i cijena moraju biti ispravni brojevi.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Greška prilikom spremanja vozila:\n"
                        + result.getMessage(),
                "Greška",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
