package view.user;

import controller.LoginController;
import controller.ReservationController;
import model.VehicleTableModel;
import model.entity.Client;
import model.entity.Reservation;
import model.entity.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Provides Swing actions for creating vehicle reservations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class VehicleReservationActions {

    private VehicleReservationActions() {
    }

    /**
     * Creates a new vehicle reservation.
     *
     * @param parent parent component used for dialogs
     * @param table vehicle table
     * @param model vehicle table model
     */
    public static void createReservation(
            VehiclesPanel parent,
            JTable table,
            VehicleTableModel model
    ) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Odaberite vozilo za rezervaciju.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Client client =
                LoginController.getLoggedInClient();

        if (client == null) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Niste prijavljeni kao korisnik.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int modelRow =
                table.convertRowIndexToModel(selectedRow);

        Vehicle vehicle =
                model.getVehicleAt(modelRow);

        JTextField fromField =
                new JTextField(
                        LocalDate.now().toString()
                );

        JTextField toField =
                new JTextField();

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                10,
                                10
                        )
                );

        panel.add(new JLabel("Vozilo:"));

        panel.add(
                new JLabel(
                        vehicle.getFullName()
                )
        );

        panel.add(
                new JLabel(
                        "Od (YYYY-MM-DD):"
                )
        );

        panel.add(fromField);

        panel.add(
                new JLabel(
                        "Do (YYYY-MM-DD):"
                )
        );

        panel.add(toField);

        int result =
                JOptionPane.showConfirmDialog(
                        parent,
                        panel,
                        "Kreiraj rezervaciju",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        LocalDate from;
        LocalDate to;

        try {
            from =
                    LocalDate.parse(
                            fromField.getText().trim()
                    );

            to =
                    LocalDate.parse(
                            toField.getText().trim()
                    );

        } catch (DateTimeParseException ex) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Datum mora biti u formatu YYYY-MM-DD.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            Reservation reservation =
                    ReservationController.createReservation(
                            vehicle,
                            client,
                            from,
                            to
                    );

            JOptionPane.showMessageDialog(
                    parent,
                    "Rezervacija uspješno kreirana!\n\n"
                            + "Vozilo: "
                            + vehicle.getFullName()
                            + "\nOd: "
                            + from
                            + "\nDo: "
                            + to
                            + "\nCijena: "
                            + String.format(
                            "%.2f €",
                            reservation.getTotalPrice()
                    ),
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            /*
             * Observer će također osvježiti tablicu
             * kada ReservationController promijeni
             * status ovog Vehicle objekta.
             *
             * Ovaj refresh zadržavamo zbog postojećeg
             * ponašanja aplikacije.
             */
            model.refresh();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    parent,
                    ex.getMessage(),
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    parent,
                    "Greška prilikom kreiranja rezervacije.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}