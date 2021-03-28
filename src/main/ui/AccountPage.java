package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPage extends JPanel implements ActionListener {
    TextField username;
    TextField firstName;
    TextField lastname;
    TextField location;

    JButton submitButton = new JButton("Submit");

    JPanel app;

    public AccountPage(JPanel app) {
        this.app = app;

        setLayout(new GridLayout(0, 2, 10, 10));

        submitButton.addActionListener(this);

        JRadioButton personalAccButton = new JRadioButton("Personal Account");
        JRadioButton businessAccButton = new JRadioButton("Business Account");

        ButtonGroup group = new ButtonGroup();
        group.add(personalAccButton);
        group.add(businessAccButton);

        add(personalAccButton);
        add(businessAccButton);

        generateTextFields();

        add(submitButton);
    }

    //MODIFY: this:
    //EFFECTS: creates text fields for username, first name, last name, and location
    public void generateTextFields() {
        username = new TextField();
        JLabel usernameLabel = new JLabel("Username:");

        firstName = new TextField();
        JLabel firstNameLabel = new JLabel("First Name:");

        lastname = new TextField();
        JLabel lastNameLabel = new JLabel("Last Name:");

        location = new TextField();
        JLabel locationLabel = new JLabel("Location:");

        add(usernameLabel);
        add(username);

        add(firstNameLabel);
        add(firstName);

        add(lastNameLabel);
        add(lastname);

        add(locationLabel);
        add(location);
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click & create account based on input

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());

        if (e.getSource() == submitButton) {
            System.out.println("account created");
            String usernameData = username.getText();
            cl.show(this.app, Pages.MENU.name());
        }
    }
}
