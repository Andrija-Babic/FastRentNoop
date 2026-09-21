package view.admin;

import model.ReservationTableModel;

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
    private final Runnable calendarRefreshAction;

    /**
     * Creates a new MainContentPanel instance.
     *
     * @param calendarRefreshAction action used to refresh the administrator calendar
     */
    public MainContentPanel(Runnable calendarRefreshAction) {

        this.calendarRefreshAction = calendarRefreshAction;

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
                        "Sve rezervacije"
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
         * Admin sees all reservations.
         *
         * refreshTable() is passed as the Observer action.
         */
        model =
                new ReservationTableModel(
                        this::refreshTable
                );

        table =
                new JTable(model);

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        return new JScrollPane(table);
    }

    // =====================================================
    // OPEN EDIT DIALOG
    // =====================================================

    private void openEditDialog() {

        int selectedRow =
                table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Molimo odaberite rezervaciju.",
                    "Nema odabrane rezervacije",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                table.convertRowIndexToModel(
                        selectedRow
                );

        var reservation =
                model.getReservationAt(
                        modelRow
                );

        JFrame parent =
                (JFrame) SwingUtilities.getWindowAncestor(
                        this
                );

        EditReservationDialog dialog =
                new EditReservationDialog(
                        parent,
                        reservation,
                        calendarRefreshAction
                );

        dialog.setVisible(true);
    }

    // =====================================================
    // REFRESH TABLE
    // =====================================================

    /**
     * Refreshes the displayed or cached data.
     */
    public void refreshTable() {

        if (model != null) {

            SwingUtilities.invokeLater(
                    model::refresh
            );
        }
    }

    // =====================================================
    // TOOLBAR ACTION
    // =====================================================

    /**
     * Performs the editselectedreservation operation.
     */
    public void editSelectedReservation() {
        openEditDialog();
    }
}