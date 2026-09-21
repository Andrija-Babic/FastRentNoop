package view.admin.calendar;

import dao.reservation.ReservationDAO;
import dao.vehicle.VehicleDAO;
import model.entity.Reservation;
import model.entity.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Builds the Swing table or UI component represented by calendartable.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class CalendarTableBuilder {

    private static final DateTimeFormatter HEADER_FORMAT =
            DateTimeFormatter.ofPattern("dd.MM");

    private CalendarTableBuilder() {
    }

    /**
     * Performs the build operation.
     *
     * @param displayedMonth supplied value used by this operation
     * @param parent supplied value used by this operation
     *
     * @return the value produced by this operation
    */
    public static JScrollPane build(
            YearMonth displayedMonth,
            Component parent
    ) {
        int daysInMonth = displayedMonth.lengthOfMonth();

        String[] columns = createColumns(displayedMonth, daysInMonth);

        DefaultTableModel model = createTableModel(columns);

        loadData(
                model,
                displayedMonth,
                daysInMonth,
                parent
        );

        JTable table = CalendarTableRenderer.createTable(
                model,
                columns
        );

        return new JScrollPane(table);
    }

    private static String[] createColumns(
            YearMonth displayedMonth,
            int daysInMonth
    ) {
        String[] columns = new String[daysInMonth + 1];

        columns[0] = "Vozilo";

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = displayedMonth.atDay(day);
            columns[day] = date.format(HEADER_FORMAT);
        }

        return columns;
    }

    private static DefaultTableModel createTableModel(
            String[] columns
    ) {
        return new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private static void loadData(
            DefaultTableModel model,
            YearMonth displayedMonth,
            int daysInMonth,
            Component parent
    ) {
        try {
            List<Vehicle> vehicles = VehicleDAO.findAll();
            List<Reservation> reservations = ReservationDAO.findAll();

            for (Vehicle vehicle : vehicles) {
                model.addRow(
                        createVehicleRow(
                                vehicle,
                                displayedMonth,
                                daysInMonth,
                                reservations
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    parent,
                    "Greška prilikom učitavanja kalendara.",
                    "Greška",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static Object[] createVehicleRow(
            Vehicle vehicle,
            YearMonth displayedMonth,
            int daysInMonth,
            List<Reservation> reservations
    ) {
        Object[] row = new Object[daysInMonth + 1];

        row[0] = vehicle.getFullName();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = displayedMonth.atDay(day);

            row[day] =
                    CalendarReservationChecker.isVehicleReserved(
                            vehicle,
                            date,
                            reservations
                    )
                            ? "X"
                            : "";
        }

        return row;
    }
}
