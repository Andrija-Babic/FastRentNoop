package view.admin;

import model.entity.Reservation;
import service.EditReservationService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing dialog used for editreservation operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditReservationDialog extends JDialog {

    private final Reservation reservation;
    private final Runnable calendarRefreshAction;

    private JTextField vehicleField;
    private JTextField clientField;
    private JTextField fromDateField;
    private JTextField toDateField;
    private JComboBox<String> statusBox;
    private JTextField priceField;

    /**
     * Creates a new EditReservationDialog instance.
     *
     * @param parent parent window of the dialog
     * @param reservation reservation being edited
     * @param calendarRefreshAction action used to refresh the administrator calendar
     */
    public EditReservationDialog(
            JFrame parent,
            Reservation reservation,
            Runnable calendarRefreshAction
    ) {
        super(parent, "Uredi rezervaciju", true);

        this.reservation = reservation;
        this.calendarRefreshAction = calendarRefreshAction;

        setSize(450, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {
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

        panel.add(new JLabel("Vozilo:"));
        vehicleField = createReadOnlyField(
                reservation.getVehicle().getFullName()
        );
        panel.add(vehicleField);
        addSpacing(panel);

        panel.add(new JLabel("Klijent:"));
        clientField = createReadOnlyField(
                reservation.getClient().getFullName()
        );
        panel.add(clientField);
        addSpacing(panel);

        panel.add(new JLabel("Od datuma:"));
        fromDateField = createReadOnlyField(
                reservation.getFrom().toString()
        );
        panel.add(fromDateField);
        addSpacing(panel);

        panel.add(new JLabel("Do datuma:"));
        toDateField = createReadOnlyField(
                reservation.getTo().toString()
        );
        panel.add(toDateField);
        addSpacing(panel);

        panel.add(new JLabel("Status:"));

        statusBox = new JComboBox<>(
                new String[]{
                        "Potvrđeno",
                        "Na čekanju",
                        "Otkazano",
                        "Izvršeno"
                }
        );

        statusBox.setSelectedItem(
                reservation.getStatus()
        );

        statusBox.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        panel.add(statusBox);
        addSpacing(panel);

        panel.add(new JLabel("Ukupna cijena:"));

        priceField = createReadOnlyField(
                reservation.getTotalPrice() + " €"
        );

        panel.add(priceField);

        return panel;
    }

    private JTextField createReadOnlyField(String value) {
        JTextField field = new JTextField(value);

        field.setEditable(false);
        field.setBackground(Color.WHITE);

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        return field;
    }

    private void addSpacing(JPanel panel) {
        panel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );
    }

    private JPanel createButtons() {
        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        panel.setBackground(Color.WHITE);
        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        JButton saveBtn =
                new JButton("Spremi");

        JButton cancelBtn =
                new JButton("Odustani");

        saveBtn.addActionListener(
                e -> saveChanges()
        );

        cancelBtn.addActionListener(
                e -> dispose()
        );

        panel.add(cancelBtn);
        panel.add(saveBtn);

        return panel;
    }

    private void saveChanges() {
        String newStatus =
                (String) statusBox.getSelectedItem();

        EditReservationService.Result result =
                EditReservationService.updateStatus(
                        reservation,
                        newStatus
                );

        if (result.isSuccess()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Status rezervacije je uspješno promijenjen.",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (calendarRefreshAction != null) {
                calendarRefreshAction.run();
            }

            dispose();
            return;
        }

        if (result.isValidationError()) {
            JOptionPane.showMessageDialog(
                    this,
                    result.getMessage(),
                    "Greška",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Greška prilikom spremanja rezervacije.",
                "Greška",
                JOptionPane.ERROR_MESSAGE
        );
    }
}