package view.admin;

import dao.client.ClientDAO;
import model.entity.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Provides user-interface actions related to clientpanel.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ClientPanelActions {

    private ClientPanelActions() {
    }

    /**
     * Performs the editselectedclient operation.
     *
     * @param parent supplied value used by this operation
     * @param table supplied value used by this operation
     * @param model supplied value used by this operation
     * @param refreshAction supplied value used by this operation
    */
    public static void editSelectedClient(
            Component parent,
            JTable table,
            DefaultTableModel model,
            Runnable refreshAction
    ) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Odaberite klijenta kojeg želite urediti.",
                    "Nije odabran klijent",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow =
                table.convertRowIndexToModel(selectedRow);

        int clientId =
                (Integer) model.getValueAt(modelRow, 0);

        Client client = ClientDAO.findById(clientId);

        if (client == null) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Klijent nije pronađen.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Window window =
                SwingUtilities.getWindowAncestor(parent);

        JFrame frame =
                window instanceof JFrame
                        ? (JFrame) window
                        : null;

        EditClientDialog dialog =
                new EditClientDialog(
                        frame,
                        client,
                        refreshAction
                );

        dialog.setVisible(true);
    }

    /**
     * Deletes the selectedclient data.
     *
     * @param parent supplied value used by this operation
     * @param table supplied value used by this operation
     * @param model supplied value used by this operation
     * @param refreshAction supplied value used by this operation
    */
    public static void deleteSelectedClient(
            Component parent,
            JTable table,
            DefaultTableModel model,
            Runnable refreshAction
    ) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Odaberite klijenta kojeg želite obrisati.",
                    "Nije odabran klijent",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int modelRow =
                table.convertRowIndexToModel(selectedRow);

        int clientId =
                (Integer) model.getValueAt(modelRow, 0);

        String firstName =
                model.getValueAt(modelRow, 1).toString();

        String lastName =
                model.getValueAt(modelRow, 2).toString();

        String fullName = firstName + " " + lastName;

        int confirmation =
                JOptionPane.showConfirmDialog(
                        parent,
                        "Jeste li sigurni da želite obrisati klijenta:\n"
                                + fullName + "?",
                        "Potvrda brisanja",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = ClientDAO.delete(clientId);

        if (deleted) {
            JOptionPane.showMessageDialog(
                    parent,
                    "Klijent je uspješno obrisan.",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            refreshAction.run();
        } else {
            JOptionPane.showMessageDialog(
                    parent,
                    "Klijent se ne može obrisati.\n\n"
                            + "Mogući razlog je da klijent ima "
                            + "postojeće rezervacije.",
                    "Brisanje nije moguće",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Refreshes the displayed or cached data.
     *
     * @param model supplied value used by this operation
    */
    public static void refreshTable(
            DefaultTableModel model
    ) {
        model.setRowCount(0);

        List<Client> clients = ClientDAO.findAll();

        for (Client client : clients) {
            model.addRow(
                    new Object[]{
                            client.getId(),
                            client.getFirstName(),
                            client.getLastName(),
                            client.isPremium()
                                    ? "Premium"
                                    : "Regular"
                    }
            );
        }
    }
}
