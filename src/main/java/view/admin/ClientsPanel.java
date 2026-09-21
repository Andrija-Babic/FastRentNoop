package view.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * Provides the Swing panel used for clients operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    /**
     * Creates a new ClientsPanel instance.
    */
    public ClientsPanel() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        add(createTopSection(), BorderLayout.NORTH);
        add(createTableSection(), BorderLayout.CENTER);
    }

    private JPanel createTopSection() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        JLabel title = new JLabel("Svi klijenti");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel searchPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.setBackground(Color.WHITE);
        searchPanel.add(new JLabel("Pretraga:"));

        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchBtn = new JButton("🔍");
        searchBtn.addActionListener(
                e -> applySearch(searchField)
        );

        searchPanel.add(searchBtn);

        container.add(title);
        container.add(searchPanel);

        return container;
    }

    private void applySearch(JTextField searchField) {

        String text =
                searchField.getText().trim();

        if (text.isEmpty()) {
            sorter.setRowFilter(null);
            return;
        }

        sorter.setRowFilter(
                RowFilter.regexFilter(
                        "(?i)" +
                                java.util.regex.Pattern.quote(text)
                )
        );
    }

    private JScrollPane createTableSection() {

        String[] columns = {
                "ID",
                "Ime",
                "Prezime",
                "Tip korisnika"
        };

        model =
                new DefaultTableModel(
                        columns,
                        0
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(25);

        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        sorter =
                new TableRowSorter<>(model);

        table.setRowSorter(sorter);

        refreshTable();

        return new JScrollPane(table);
    }

    private void editSelectedClient() {

        ClientPanelActions.editSelectedClient(
                this,
                table,
                model,
                this::refreshTable
        );
    }

    private void deleteSelectedClient() {

        ClientPanelActions.deleteSelectedClient(
                this,
                table,
                model,
                this::refreshTable
        );
    }

    /**
     * Refreshes the displayed or cached data.
    */
    public void refreshTable() {

        if (model == null) {
            return;
        }

        ClientPanelActions.refreshTable(model);
    }

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
    public DefaultTableModel getModel() {
        return model;
    }
}
