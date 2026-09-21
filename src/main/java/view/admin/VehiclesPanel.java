package view.admin;

import model.VehicleTableModel;
import model.entity.Vehicle;
import model.observer.Observer;
import model.observer.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
                        "Sva vozila"
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
                        20,
                        0,
                        10,
                        0
                )
        );

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        filterPanel.setBackground(Color.WHITE);

        filterPanel.add(
                new JLabel("Pretraga:")
        );

        JTextField searchField =
                new JTextField(20);

        filterPanel.add(searchField);

        JButton searchBtn =
                new JButton("🔍");

        searchBtn.addActionListener(
                e -> applySearch(searchField)
        );

        filterPanel.add(searchBtn);

        container.add(title);
        container.add(filterPanel);

        return container;
    }

    private void applySearch(
            JTextField searchField
    ) {

        String text =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        if (text.isEmpty()) {
            table.setRowSorter(null);
            return;
        }

        javax.swing.table.TableRowSorter<
                javax.swing.table.TableModel
                > sorter =
                new javax.swing.table.TableRowSorter<>(
                        table.getModel()
                );

        sorter.setRowFilter(
                javax.swing.RowFilter.regexFilter(
                        "(?i)"
                                + java.util.regex.Pattern.quote(text)
                )
        );

        table.setRowSorter(sorter);
    }

    private JScrollPane createTableSection() {

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        applyStatusRenderer();

        return new JScrollPane(table);
    }

    private void applyStatusRenderer() {

        if (table.getColumnModel()
                .getColumnCount() <= 4) {

            return;
        }

        table.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new VehicleStatusRenderer()
                );
    }

    /**
     * Refreshes the displayed or cached data.
    */
    public void refreshTable() {

        if (model != null) {
            model.refresh();
        }

        applyStatusRenderer();
    }

    @Override
    public void update(Subject subject) {

        if (!(subject instanceof Vehicle)) {
            return;
        }

        SwingUtilities.invokeLater(
                this::refreshTable
        );
    }

    /**
     * Returns the table.
     *
     * @return the value produced by this operation
    */
    public JTable getTable() {
        return table;
    }
}
