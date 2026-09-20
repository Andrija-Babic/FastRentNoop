package view.user;

import model.VehicleTableModel;
import model.entity.Vehicle;
import model.observer.Observer;
import model.observer.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Provides the Swing panel used for vehicles operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehiclesPanel extends JPanel implements Observer {

    private VehicleTableModel model;
    private JTable table;

    /**
     * Creates a new VehiclesPanel instance.
    */
    public VehiclesPanel() {

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

        /*
         * false = prikaži sva vozila.
         *
         * Dostupnost za konkretne datume provjerava
         * ReservationController.
         *
         * this = VehiclesPanel kao Observer.
         */
        model =
                new VehicleTableModel(
                        false,
                        this
                );

        table =
                new JTable(model);

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

        JPanel container =
                new JPanel();

        container.setLayout(
                new BoxLayout(
                        container,
                        BoxLayout.Y_AXIS
                )
        );

        container.setBackground(Color.WHITE);

        JLabel title =
                new JLabel(
                        "Ponuda vozila"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        title.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        filterPanel.setBackground(
                Color.WHITE
        );

        filterPanel.add(
                new JLabel("Pretraga:")
        );

        JTextField searchField =
                new JTextField(20);

        filterPanel.add(
                searchField
        );

        JButton searchBtn =
                new JButton("🔍");

        searchBtn.addActionListener(
                e -> applySearch(searchField)
        );

        filterPanel.add(
                searchBtn
        );

        container.add(title);
        container.add(filterPanel);

        return container;
    }

    // =====================================================
    // SEARCH
    // =====================================================

    private void applySearch(
            JTextField searchField
    ) {

        String search =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        if (search.isEmpty()) {

            table.setRowSorter(null);

            return;
        }

        TableRowSorter<VehicleTableModel> sorter =
                new TableRowSorter<>(
                        model
                );

        sorter.setRowFilter(
                RowFilter.regexFilter(
                        "(?i)" + java.util.regex.Pattern.quote(search)
                )
        );

        table.setRowSorter(sorter);
    }

    // =====================================================
    // TABLE SECTION
    // =====================================================

    private JScrollPane createTableSection() {

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.removeColumn(
                table.getColumnModel().getColumn(0)
        );

        /*
         * Nakon skrivanja ID-a:
         *
         * 0 = Marka i model
         * 1 = Godina
         * 2 = Cijena po danu
         * 3 = Status
         */

        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new VehicleStatusRenderer()
                );

        return new JScrollPane(table);
    }

    // =====================================================
    // REFRESH
    // =====================================================

    /**
     * Refreshes the displayed or cached data.
    */
    public void refreshTable() {

        model.refresh();

        /*
         * Refreshing the model can recreate the table data,
         * so keep the status renderer applied.
         */
        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new VehicleStatusRenderer()
                );
    }

    // =====================================================
    // TOOLBAR GETTERS
    // =====================================================

    /**
     * Returns the model.
     *
     * @return the value produced by this operation
    */
    public VehicleTableModel getModel() {
        return model;
    }

    /**
     * Returns the table.
     *
     * @return the value produced by this operation
    */
    public JTable getTable() {
        return table;
    }

    // =====================================================
    // OBSERVER
    // =====================================================

    @Override
    public void update(
            Subject subject
    ) {

        if (subject instanceof Vehicle) {

            SwingUtilities.invokeLater(
                    this::refreshTable
            );
        }
    }
}
