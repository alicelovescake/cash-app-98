package ui;

import model.BusinessUser;
import model.BusinessUser.BusinessType;
import model.PersonalUser;
import model.User;
import ui.Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountPage extends JPanel implements ActionListener {
    TextField username;
    TextField firstName;
    TextField lastname;
    TextField location;
    TextField businessName;
    ButtonGroup group;
    ButtonGroup businessTypeGroup;

    JButton submitButton = new JButton("Submit");

    JPanel app;

    public AccountPage(JPanel app) {
        this.app = app;

        setLayout(new GridLayout(0, 2, 10, 10));

        submitButton.addActionListener(this);

        JRadioButton personalAccButton = new JRadioButton("Personal Account");
        personalAccButton.setActionCommand("Personal");
        JRadioButton businessAccButton = new JRadioButton("Business Account");
        businessAccButton.setActionCommand("Business");
        group = new ButtonGroup();
        group.add(personalAccButton);
        group.add(businessAccButton);

        JRadioButton cafeButton = new JRadioButton(BusinessType.CAFE.name());
        cafeButton.setActionCommand("cafe");
        JRadioButton groceryButton = new JRadioButton(BusinessType.GROCERY.name());
        groceryButton.setActionCommand("grocery");
        JRadioButton retailButton = new JRadioButton(BusinessType.RETAILER.name());
        retailButton.setActionCommand("retail");
        JRadioButton resButton = new JRadioButton(BusinessType.RESTAURANT.name());
        resButton.setActionCommand("rest");

        businessTypeGroup = new ButtonGroup();
        businessTypeGroup.add(cafeButton);
        businessTypeGroup.add(groceryButton);
        businessTypeGroup.add(retailButton);
        businessTypeGroup.add(resButton);

        add(personalAccButton);
        add(businessAccButton);
        add(cafeButton);
        add(groceryButton);
        add(retailButton);
        add(resButton);
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

        businessName = new TextField();
        JLabel businessNameLabel = new JLabel("Business Name (if applicable):");

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

        add(businessNameLabel);
        add(businessName);
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click & create account based on input

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());
        User createdUser;
        BusinessType type;
        type = findType();

        if (e.getSource() == submitButton) {
            String usernameData = username.getText();
            String firstNameData = firstName.getText();
            String lastNameData = lastname.getText();
            String locationData = location.getText();
            String businessNameData = businessName.getText();
            if (group.getSelection().getActionCommand().equals("Personal")) {
                createdUser = new PersonalUser(usernameData, locationData, firstNameData, lastNameData);
            } else {
                createdUser = new BusinessUser(usernameData, locationData, businessNameData, type);
            }

            MainApp.setUser(createdUser);
            JLabel successLabel = new JLabel("It's great for you to join us from "
                    + locationData + "!" + " Your username $" + usernameData + " is ready to be used! Woooot!");
            //TODO: Success message on top of menu page
            cl.show(this.app, Pages.MENU.name());
        }
    }

    //EFFECTS: determines business type based on user input
    public BusinessType findType() {
        BusinessType type;
        switch (businessTypeGroup.getSelection().getActionCommand()) {
            case "cafe":
                type = BusinessType.CAFE;
                break;
            case "res":
                type = BusinessType.RESTAURANT;
                break;
            case "retail":
                type = BusinessType.RETAILER;
                break;
            case "grocery":
                type = BusinessType.GROCERY;
                break;
            default: type = BusinessType.OTHER;
        }

        return type;
    }
}
