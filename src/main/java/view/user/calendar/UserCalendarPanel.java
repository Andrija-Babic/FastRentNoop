package view.user.calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Provides the Swing panel used for usercalendar operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class UserCalendarPanel extends JPanel {

    private YearMonth displayedMonth;

    private JLabel monthLabel;

    /**
     * Creates a new UserCalendarPanel instance.
    */
    public UserCalendarPanel() {

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        displayedMonth = YearMonth.now();

        add(createTopSection(), BorderLayout.NORTH);
        add(createCalendarTable(), BorderLayout.CENTER);
    }

    private JPanel createTopSection() {

        JPanel container = new JPanel(new BorderLayout());

        container.setBackground(Color.WHITE);

        JLabel title = new JLabel("Moj kalendar rezervacija");

        title.setFont(new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        title.setBorder(new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );

        container.add(
                title,
                BorderLayout.NORTH
        );

        JPanel navigationPanel =
                new JPanel(new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        navigationPanel.setBackground(Color.WHITE);

        JButton previousButton = new JButton("‹ Prethodni mjesec");

        previousButton.addActionListener(e -> {

            displayedMonth = displayedMonth.minusMonths(1);

            refreshCalendar();
        });

        JButton todayButton = new JButton("Danas");

        todayButton.addActionListener(e -> {

            displayedMonth = YearMonth.now();

            refreshCalendar();
        });

        JButton nextButton = new JButton("Sljedeći mjesec ›");

        nextButton.addActionListener(e -> {

            displayedMonth = displayedMonth.plusMonths(1);

            refreshCalendar();
        });

        monthLabel = new JLabel();

        monthLabel.setFont(new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        updateMonthLabel();

        navigationPanel.add(previousButton);
        navigationPanel.add(todayButton);
        navigationPanel.add(monthLabel);
        navigationPanel.add(nextButton);

        container.add(
                navigationPanel,
                BorderLayout.CENTER
        );

        return container;
    }

    private JScrollPane createCalendarTable() {

        return UserCalendarTableBuilder.createCalendarTable(
                displayedMonth,
                this
        );
    }

    private void updateMonthLabel() {

        String month = displayedMonth.getMonth().getDisplayName(
                                TextStyle.FULL,
                                new Locale("hr", "HR")
                        );

        month = month.substring(0, 1).toUpperCase() + month.substring(1);

        monthLabel.setText(month + " " + displayedMonth.getYear());
    }

    private void refreshCalendar() {

        updateMonthLabel();

        removeAll();

        add(
                createTopSection(),
                BorderLayout.NORTH
        );

        add(
                createCalendarTable(),
                BorderLayout.CENTER
        );

        revalidate();
        repaint();
    }

    /**
     * Refreshes the displayed or cached data.
    */
    public void refreshTable() {refreshCalendar();}
}
