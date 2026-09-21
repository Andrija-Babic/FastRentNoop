package view.admin;

import model.entity.Client;
import model.strategy.PricingType;
import service.EditClientService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Provides the Swing dialog used for editclient operations.
 *
 * <p>This class is part of the FastRent application architecture.</p>
 */
public class EditClientDialog extends JDialog {

    private final Client client;
    private final Runnable refreshAction;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<PricingType> pricingTypeComboBox;

    /**
     * Creates a new EditClientDialog instance.
     *
     * @param parent supplied value used by this operation
     * @param client supplied value used by this operation
     * @param refreshAction supplied value used by this operation
     */
    public EditClientDialog(
            JFrame parent,
            Client client,
            Runnable refreshAction
    ) {
        super(parent, "Uredi klijenta", true);

        this.client = client;
        this.refreshAction = refreshAction;

        setSize(400, 430);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        add(createForm(), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        panel.add(createLabel("Ime:"));
        firstNameField = createTextField(client.getFirstName());
        panel.add(firstNameField);
        addSpacing(panel);

        panel.add(createLabel("Prezime:"));
        lastNameField = createTextField(client.getLastName());
        panel.add(lastNameField);
        addSpacing(panel);

        panel.add(createLabel("Korisničko ime:"));

        String username =
                client.getUsername() == null
                        ? ""
                        : client.getUsername();

        usernameField = createTextField(username);
        panel.add(usernameField);
        addSpacing(panel);

        panel.add(createLabel("Nova lozinka:"));
        passwordField = new JPasswordField();
        configureField(passwordField);
        panel.add(passwordField);
        addSpacing(panel);

        panel.add(createLabel("Tip cjenika:"));

        pricingTypeComboBox =
                new JComboBox<>(PricingType.values());

        pricingTypeComboBox.setSelectedItem(
                client.getPricingType()
        );

        configureField(pricingTypeComboBox);
        panel.add(pricingTypeComboBox);

        return panel;
    }

    private JLabel createLabel(String text) {
        return new JLabel(text);
    }

    private JTextField createTextField(String value) {
        JTextField field = new JTextField(value);
        configureField(field);
        return field;
    }

    private void configureField(JComponent field) {
        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 30)
        );
    }

    private void addSpacing(JPanel panel) {
        panel.add(
                Box.createRigidArea(
                        new Dimension(0, 15)
                )
        );
    }

    private JPanel createButtons() {
        JPanel panel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        panel.setBackground(Color.WHITE);
        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        JButton cancelButton = new JButton("Odustani");
        JButton saveButton = new JButton("Spremi");

        cancelButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> saveClient());

        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    private void saveClient() {
        String firstName =
                firstNameField.getText().trim();

        String lastName =
                lastNameField.getText().trim();

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        PricingType pricingType =
                (PricingType) pricingTypeComboBox.getSelectedItem();

        EditClientService.Result result =
                EditClientService.updateClient(
                        client,
                        firstName,
                        lastName,
                        username,
                        password,
                        pricingType
                );

        if (result == EditClientService.Result.SUCCESS) {
            if (refreshAction != null) {
                refreshAction.run();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Klijent je uspješno ažuriran.",
                    "Uspjeh",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            return;
        }

        showResult(result);
    }

    private void showResult(
            EditClientService.Result result
    ) {
        switch (result) {
            case EMPTY_FIRST_NAME ->
                    showWarning(
                            "Ime ne smije biti prazno."
                    );

            case EMPTY_LAST_NAME ->
                    showWarning(
                            "Prezime ne smije biti prazno."
                    );

            case ADMIN_USERNAME ->
                    showWarning(
                            "Korisničko ime 'admin' nije dostupno."
                    );

            case PASSWORD_TOO_SHORT ->
                    showWarning(
                            "Lozinka mora imati najmanje 6 znakova."
                    );

            case USERNAME_TAKEN ->
                    showWarning(
                            "Korisničko ime je već zauzeto."
                    );

            case UPDATE_ERROR ->
                    showError(
                            "Greška prilikom spremanja izmjena."
                    );

            case CREDENTIALS_ERROR ->
                    showError(
                            "Podaci klijenta su spremljeni, "
                                    + "ali nije moguće spremiti "
                                    + "podatke za prijavu."
                    );

            case SUCCESS -> {
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

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Greška",
                JOptionPane.ERROR_MESSAGE
        );
    }
}