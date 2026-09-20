package view.user;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Renders vehicle status values in a Swing table.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class VehicleStatusRenderer
        extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {
        Component c =
                super.getTableCellRendererComponent(
                        table,
                        value,
                        isSelected,
                        hasFocus,
                        row,
                        column
                );

        if (column == 3 && value != null) {

            String status =
                    value.toString();

            if (status.equalsIgnoreCase(
                    "Dostupno"
            )) {

                c.setForeground(
                        new Color(
                                0,
                                128,
                                0
                        )
                );

            } else {

                c.setForeground(Color.RED);
            }

        } else {

            c.setForeground(Color.BLACK);
        }

        return c;
    }
}
