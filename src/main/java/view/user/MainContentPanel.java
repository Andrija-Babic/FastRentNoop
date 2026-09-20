package view.user;

import controller.LoginController;
import controller.ReservationController;
import model.ReservationTableModel;
import model.entity.Reservation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing panel used for maincontent operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class MainContentPanel extends JPanel {

    private JTable table;
    private ReservationTableModel model;

    /**
     * Creates a new MainContentPanel instance.
    */
    public MainContentPanel() {

        setLayout(new BorderLayout());

        setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        setBackground(Color.WHITE);

        add(
                createTopSection(),
                BorderLayout.NORTH
        );

        add(
                createTableSection(),
                BorderLayout.CENTER
        );
    }

    // =====================================================
    // TOP SECTION
    // =====================================================

    private JPanel createTopSection() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        panel.setBackground(Color.WHITE);

        JLabel title =
                new JLabel(
                        "Moje rezervacije"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        panel.add(title);

        return panel;
    }

    // =====================================================
    // TABLE SECTION
    // =====================================================

    private JScrollPane createTableSection() {

        /*
         * Get the currently logged-in client.
         */
        if (LoginController.getLoggedInClient() == null) {

            model =
                    new ReservationTableModel();

        } else {

            String loggedUser =
                    LoginController
                            .getLoggedInClient()
                            .getFullName();

            /*
             * this::refreshTable is a Runnable.
             *
             * When ReservationStatusObserver detects
             * a status change, it can call refreshTable().
             */
            model =
                    new ReservationTableModel(
                            loggedUser,
                            this::refreshTable
                    );
        }

        table =
                new JTable(model);

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        return new JScrollPane(table);
    }

    // =====================================================
    // REFRESH TABLE
    // =====================================================

    /**
     * Refreshes the displayed or cached data.
    */
    public void refreshTable() {

        if (model != null) {

            model.refresh();
        }
    }

    // =====================================================
    // SHOW RESERVATION
    // =====================================================

    /**
     * Shows the requested user-interface content.
    */
    public void showSelectedReservation() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Odaberite rezervaciju.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        Reservation reservation =
                model.getReservationAt(
                        modelRow
                );

        String message =
                "Vozilo: "
                        + reservation
                        .getVehicle()
                        .getFullName()
                        + "\n"
                        + "Korisnik: "
                        + reservation
                        .getClient()
                        .getFullName()
                        + "\n"
                        + "Od: "
                        + reservation.getFrom()
                        + "\n"
                        + "Do: "
                        + reservation.getTo()
                        + "\n"
                        + "Cijena: "
                        + reservation.getTotalPrice()
                        + " €"
                        + "\n"
                        + "Status: "
                        + reservation.getStatus();

        JOptionPane.showMessageDialog(
                this,
                message,
                "Prikaži rezervaciju",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =====================================================
    // CANCEL RESERVATION
    // =====================================================

    /**
     * Performs the cancelselectedreservation operation.
    */
    public void cancelSelectedReservation() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Odaberite rezervaciju.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        Reservation reservation =
                model.getReservationAt(
                        modelRow
                );

        if (!"Potvrđeno".equalsIgnoreCase(
                reservation.getStatus()
        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Samo potvrđene rezervacije mogu biti otkazane.",
                    "Upozorenje",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Želite li otkazati odabranu rezervaciju?",
                        "Otkaži rezervaciju",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            ReservationController.updateReservationStatus(
                    reservation,
                    "Otkazano"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Rezervacija je uspješno otkazana.",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            /*
             * ReservationController updates the Reservation
             * object, which triggers the existing Observer.
             *
             * Refresh is kept here so the table immediately
             * reflects the new status.
             */
            refreshTable();

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Greška prilikom otkazivanja rezervacije.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // GETTERS
    // =====================================================

    /**
     * Returns the table.
     *
     * @return the value produced by this operation
    */
    public JTable getTable() {
        return table;
    }

    /**
     * Returns the model.
     *
     * @return the value produced by this operation
    */
    public ReservationTableModel getModel() {
        return model;
    }
}
