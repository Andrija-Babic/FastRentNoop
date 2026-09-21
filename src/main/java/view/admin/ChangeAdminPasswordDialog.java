package view.admin;

import service.AdminPasswordService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing dialog used for changeadminpassword operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class ChangeAdminPasswordDialog extends JDialog {

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    /**
     * Creates a new ChangeAdminPasswordDialog instance.
     *
     * @param parent supplied value used by this operation
    */
    public ChangeAdminPasswordDialog(JFrame parent) {
        super(parent, "Promijeni lozinku", true);

        setSize(400, 330);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 25, 10, 25));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel(
                "Promjena administratorske lozinke"
        );
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(createLabel("Trenutna lozinka:"));
        currentPasswordField = createPasswordField();
        panel.add(currentPasswordField);

        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        panel.add(createLabel("Nova lozinka:"));
        newPasswordField = createPasswordField();
        panel.add(newPasswordField);

        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        panel.add(createLabel("Ponovi novu lozinku:"));
        confirmPasswordField = createPasswordField();
        panel.add(confirmPasswordField);

        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton cancelButton = new JButton("Odustani");
        cancelButton.setPreferredSize(new Dimension(110, 35));
        cancelButton.addActionListener(e -> dispose());

        JButton saveButton = new JButton("Spremi");
        saveButton.setPreferredSize(new Dimension(110, 35));
        saveButton.addActionListener(e -> changePassword());

        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    private void changePassword() {
        String currentPassword =
                new String(currentPasswordField.getPassword());

        String newPassword =
                new String(newPasswordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        AdminPasswordService.Result result =
                AdminPasswordService.changePassword(
                        currentPassword,
                        newPassword,
                        confirmPassword
                );

        if (result == AdminPasswordService.Result.SUCCESS) {
            JOptionPane.showMessageDialog(
                    this,
                    "Administratorska lozinka je uspješno promijenjena.",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
            return;
        }

        showError(result);
    }

    private void showError(AdminPasswordService.Result result) {
        switch (result) {
            case EMPTY_FIELDS -> showWarning(
                    "Molimo ispunite sva polja."
            );

            case PASSWORD_TOO_SHORT -> showWarning(
                    "Nova lozinka mora imati najmanje 6 znakova."
            );

            case PASSWORDS_NOT_MATCHING -> showWarning(
                    "Nove lozinke se ne podudaraju."
            );

            case ADMIN_NOT_FOUND -> showErrorMessage(
                    "Administratorski račun nije pronađen."
            );

            case INVALID_CURRENT_PASSWORD -> showWarning(
                    "Trenutna lozinka nije ispravna."
            );

            case SAVE_ERROR -> showErrorMessage(
                    "Greška prilikom spremanja nove lozinke."
            );

            case SUCCESS -> {
                // Handled in changePassword().
            }
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Greška",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
