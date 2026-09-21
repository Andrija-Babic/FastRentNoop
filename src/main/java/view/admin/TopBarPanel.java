package view.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the top header area of the corresponding FastRent view.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class TopBarPanel extends JPanel {

    /**
     * Creates a new TopBarPanel instance.
    */
    public TopBarPanel() {

        setLayout(new BorderLayout());

        setBorder(
                new EmptyBorder(
                        10,
                        20,
                        10,
                        20
                )
        );

        setBackground(Color.WHITE);

        JLabel logo =
                new JLabel("🚗 FastRent");

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        add(
                logo,
                BorderLayout.WEST
        );
    }
}
