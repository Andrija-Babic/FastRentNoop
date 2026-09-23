package view.admin;

import model.ReservationTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing panel used for displaying administrator reservations.
 *
 * <p>The panel displays executed and unexecuted reservations
 * in separate tables.</p>
 */
public class MainContentPanel extends JPanel {

    private JTable executedTable;
    private JTable unexecutedTable;

    private ReservationTableModel executedModel;
    private ReservationTableModel unexecutedModel;

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
                        "Rezervacije"
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

    private JPanel createTableSection() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                2,
                                1,
                                0,
                                15
                        )
                );

        panel.setBackground(Color.WHITE);

        // -------------------------------------------------
        // EXECUTED RESERVATIONS
        // -------------------------------------------------

        executedModel =
                new ReservationTableModel(
                        this::refreshTable,
                        "Izvršeno"
                );

        executedTable =
                createTable(
                        executedModel
                );

        JPanel executedPanel =
                new JPanel(
                        new BorderLayout()
                );

        executedPanel.setBackground(Color.WHITE);

        JLabel executedTitle =
                new JLabel(
                        "Izvršene rezervacije"
                );

        executedTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        executedPanel.add(
                executedTitle,
                BorderLayout.NORTH
        );

        executedPanel.add(
                new JScrollPane(
                        executedTable
                ),
                BorderLayout.CENTER
        );

        panel.add(executedPanel);

        // -------------------------------------------------
        // UNEXECUTED RESERVATIONS
        // -------------------------------------------------

        unexecutedModel =
                new ReservationTableModel(
                        this::refreshTable,
                        "Potvrđeno"
                );

        unexecutedTable =
                createTable(
                        unexecutedModel
                );

        JPanel unexecutedPanel =
                new JPanel(
                        new BorderLayout()
                );

        unexecutedPanel.setBackground(Color.WHITE);

        JLabel unexecutedTitle =
                new JLabel(
                        "Neizvršene rezervacije"
                );

        unexecutedTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        unexecutedPanel.add(
                unexecutedTitle,
                BorderLayout.NORTH
        );

        unexecutedPanel.add(
                new JScrollPane(
                        unexecutedTable
                ),
                BorderLayout.CENTER
        );

        panel.add(unexecutedPanel);

        return panel;
    }

    // =====================================================
    // CREATE TABLE
    // =====================================================

    private JTable createTable(
            ReservationTableModel model) {

        JTable table =
                new JTable(model);

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        return table;
    }

    // =====================================================
    // OPEN EDIT DIALOG
    // =====================================================

    private void openEditDialog() {

        JTable selectedTable = null;
        ReservationTableModel selectedModel = null;

        if (executedTable.getSelectedRow() != -1) {

            selectedTable =
                    executedTable;

            selectedModel =
                    executedModel;

        } else if (unexecutedTable.getSelectedRow() != -1) {

            selectedTable =
                    unexecutedTable;

            selectedModel =
                    unexecutedModel;
        }

        if (selectedTable == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Molimo odaberite rezervaciju.",
                    "Nema odabrane rezervacije",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int selectedRow =
                selectedTable.getSelectedRow();

        int modelRow =
                selectedTable.convertRowIndexToModel(
                        selectedRow
                );

        var reservation =
                selectedModel.getReservationAt(
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
     * Refreshes both reservation tables.
     */
    public void refreshTable() {

        SwingUtilities.invokeLater(() -> {

            if (executedModel != null) {

                executedModel.refresh();
            }

            if (unexecutedModel != null) {

                unexecutedModel.refresh();
            }
        });
    }

    // =====================================================
    // TOOLBAR ACTION
    // =====================================================

    /**
     * Performs the edit selected reservation operation.
     */
    public void editSelectedReservation() {

        openEditDialog();
    }
}