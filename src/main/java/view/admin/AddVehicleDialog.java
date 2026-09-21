package view.admin;

import controller.VehicleController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing dialog used for addvehicle operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class AddVehicleDialog extends JDialog {

    private JTextField brandField;
    private JTextField yearField;
    private JTextField typeField;
    private JTextField priceField;
    private JComboBox<String> statusBox;

    /**
     * Creates a new AddVehicleDialog instance.
     *
     * @param parent supplied value used by this operation
    */
    public AddVehicleDialog(JFrame parent) {
        super(parent, "Dodaj vozilo", true);

        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        brandField = createInputField(
                panel,
                "Marka i model:"
        );

        yearField = createInputField(
                panel,
                "Godina:"
        );

        typeField = createInputField(
                panel,
                "Tip vozila:"
        );

        priceField = createInputField(
                panel,
                "Cijena po danu:"
        );

        panel.add(new JLabel("Status:"));

        statusBox = new JComboBox<>(new String[]{
                "Dostupno",
                "Iznajmljeno",
                "Servis"
        });

        statusBox.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );

        panel.add(statusBox);

        return panel;
    }

    private JTextField createInputField(
            JPanel panel,
            String labelText) {

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField field = new JTextField();

        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );

        panel.add(label);
        panel.add(Box.createRigidArea(
                new Dimension(0, 5)
        ));

        panel.add(field);

        panel.add(Box.createRigidArea(
                new Dimension(0, 15)
        ));

        return field;
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        panel.setBackground(Color.WHITE);

        JButton saveBtn = new JButton("Spremi");
        JButton cancelBtn = new JButton("Odustani");

        saveBtn.addActionListener(
                e -> saveVehicle()
        );

        cancelBtn.addActionListener(
                e -> dispose()
        );

        panel.add(cancelBtn);
        panel.add(saveBtn);

        return panel;
    }

    private void saveVehicle() {

        String brandModel = brandField.getText().trim();

        String yearText = yearField.getText().trim();

        String type = typeField.getText().trim();

        String priceText = priceField.getText().trim();

        String status = (String) statusBox.getSelectedItem();

        if (brandModel.isEmpty()
                || yearText.isEmpty()
                || type.isEmpty()
                || priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Sva polja moraju biti popunjena.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            int year = Integer.parseInt(yearText);

            double price = Double.parseDouble(priceText);

            /*
             * VehicleController trenutno očekuje:
             *
             * addVehicle(
             *     String brandModel,
             *     int year,
             *     double price,
             *     String status
             * )
             */

            VehicleController.addVehicle(
                    brandModel,
                    year,
                    price,
                    status
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Vozilo uspješno dodano!",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Godina mora biti cijeli broj, "
                            + "a cijena mora biti broj.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
