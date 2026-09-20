package view.user.calendar;

import model.entity.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Builds the Swing table or UI component represented by usercalendartable.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public final class UserCalendarTableBuilder {

    private static final DateTimeFormatter HEADER_FORMAT = DateTimeFormatter.ofPattern("dd.MM");

    private UserCalendarTableBuilder() {
    }

    /**
     * Creates or stores the data handled by this method.
     *
     * @param displayedMonth supplied value used by this operation
     * @param parent supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static JScrollPane createCalendarTable(
            YearMonth displayedMonth,
            Component parent
    ) {

        int daysInMonth = displayedMonth.lengthOfMonth();

        String[] columns = new String[daysInMonth + 1];

        columns[0] = "Vozilo";

        for (int i = 1; i <= daysInMonth; i++) {

            LocalDate date = displayedMonth.atDay(i);

            columns[i] = date.format(HEADER_FORMAT);
        }

        List<Reservation> reservations = UserCalendarReservationHelper.loadActiveReservations(parent);

        Object[][] data = new Object[reservations.size()][daysInMonth + 1];

        for (int row = 0; row < reservations.size(); row++) {

            Reservation reservation = reservations.get(row);

            data[row][0] = reservation.getVehicle().getFullName();

            for (int day = 1; day <= daysInMonth; day++) {

                LocalDate currentDate = displayedMonth.atDay(day);

                if (UserCalendarReservationHelper.isDateReserved(reservation, currentDate)) {

                    data[row][day] = "X";

                } else {

                    data[row][day] = "";
                }
            }
        }

        DefaultTableModel model =
                new DefaultTableModel(data, columns) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return false;
                    }
                };

        JTable table = new JTable(model);

        table.setRowHeight(35);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        DefaultTableCellRenderer vehicleRenderer = new DefaultTableCellRenderer();

        vehicleRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        table.getColumnModel().getColumn(0).setCellRenderer(vehicleRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(160);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

                    @Override
                    public Component
                    getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component c = super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        setHorizontalAlignment(SwingConstants.CENTER);

                        if (column > 0 && value != null && value.equals("X")) {

                            c.setBackground(new Color(220, 53, 69));

                            c.setForeground(Color.WHITE);

                        } else {

                            c.setBackground(Color.WHITE);

                            c.setForeground(Color.BLACK);
                        }

                        return c;
                    }
                };

        for (int i = 1; i < columns.length; i++) {

            table.getColumnModel().getColumn(i).setCellRenderer(renderer);

            table.getColumnModel().getColumn(i).setPreferredWidth(60);
        }

        return new JScrollPane(table);
    }
}
