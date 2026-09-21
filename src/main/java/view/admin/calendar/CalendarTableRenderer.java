package view.admin.calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Provides Swing table rendering for calendartable values.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class CalendarTableRenderer {

    private CalendarTableRenderer() {
    }

    /**
     * Creates or stores the data handled by this method.
     *
     * @param model supplied value used by this operation
     * @param columns supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static JTable createTable(
            TableModel model,
            String[] columns
    ) {
        JTable table = new JTable(model);

        table.setRowHeight(35);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        configureVehicleColumn(table);
        configureDateColumns(table, columns);

        return table;
    }

    private static void configureVehicleColumn(JTable table) {
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer();

        renderer.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(renderer);

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(180);
    }

    private static void configureDateColumns(
            JTable table,
            String[] columns
    ) {
        DefaultTableCellRenderer renderer =
                new DateCellRenderer();

        for (int column = 1; column < columns.length; column++) {
            table.getColumnModel()
                    .getColumn(column)
                    .setCellRenderer(renderer);

            table.getColumnModel()
                    .getColumn(column)
                    .setPreferredWidth(60);
        }
    }

    private static class DateCellRenderer
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
            Component component =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            if (column > 0
                    && value != null
                    && value.equals("X")) {

                component.setBackground(
                        new Color(220, 53, 69)
                );

                component.setForeground(Color.WHITE);

            } else {

                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
            }

            return component;
        }
    }
}
