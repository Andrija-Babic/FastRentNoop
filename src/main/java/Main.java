import database.DatabaseInitializer;
import view.login.LoginFrame;

import javax.swing.*;

/**
 * Represents the Main component used by the FastRent application.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class Main {

    /**
     * Performs the main operation.
     *
     * @param args supplied value used by this operation
    */
    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        SwingUtilities.invokeLater(() ->
                new LoginFrame().setVisible(true)
        );
    }
}
