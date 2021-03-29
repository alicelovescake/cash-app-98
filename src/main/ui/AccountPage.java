package ui;

import com.sun.tools.javac.comp.Flow;
import model.BusinessUser;
import model.BusinessUser.BusinessType;
import model.PersonalUser;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
//class to create account creation page that accepts user input to create an account

public class AccountPage extends JPanel implements ActionListener {
    TextField username;
    TextField firstName;
    TextField lastname;
    TextField location;
    TextField businessName;
    ButtonGroup group;
    ButtonGroup businessTypeGroup;
    JRadioButton personalAccButton;
    JRadioButton businessAccButton;
    JRadioButton cafeButton;
    JRadioButton groceryButton;
    JRadioButton retailButton;
    JRadioButton resButton;

    JButton submitButton = new JButton("Create Account");

    MainApp app;

//MODIFY: this
//EFFECTS: creates page that displays and adds a table to app with completed, pending, and failed transactions
    public AccountPage(MainApp app) {
        this.app = app;

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                removeAll();

                createPage();

                revalidate();
                repaint();
            }
        });

        submitButton.addActionListener(this);

        setOpaque(false);
    }

    //MODIFY: this
    // EFFECTS: Creates account page with radio buttons and text fields and adds to this panel
    public void createPage() {
        new PageTitle(this, "Create Account");

        initializeAccountRadioButtons();

        add(Box.createRigidArea(new Dimension(400, 25)));

        initializeBusinessRadioButtons();

        add(Box.createRigidArea(new Dimension(400, 25)));

        generateTextFields();

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(submitButton);
    }

    //EFFECTS: instantiates account radio buttons and sets action command
    public void initializeAccountRadioButtons() {
        JPanel accountButtons = new JPanel();
        accountButtons.setLayout(new GridLayout(1, 0, 10, 10));
        accountButtons.setOpaque(false);
        accountButtons.setPreferredSize(new Dimension(400, 20));

        personalAccButton = new JRadioButton("Personal Account");
        personalAccButton.setActionCommand("Personal");

        businessAccButton = new JRadioButton("Business Account");
        businessAccButton.setActionCommand("Business");

        group = new ButtonGroup();
        group.add(personalAccButton);
        group.add(businessAccButton);

        accountButtons.add(personalAccButton);
        accountButtons.add(businessAccButton);

        add(accountButtons);
    }

    //EFFECTS: instantiates all radio buttons and sets action command
    public void initializeBusinessRadioButtons() {
        JPanel businessButtons = new JPanel();
        businessButtons.setLayout(new GridLayout(0, 2, 10, 10));
        businessButtons.setOpaque(false);
        businessButtons.setPreferredSize(new Dimension(400, 50));

        cafeButton = new JRadioButton(BusinessType.CAFE.name());
        cafeButton.setActionCommand("cafe");

        groceryButton = new JRadioButton(BusinessType.GROCERY.name());
        groceryButton.setActionCommand("grocery");

        retailButton = new JRadioButton(BusinessType.RETAILER.name());
        retailButton.setActionCommand("retail");

        resButton = new JRadioButton(BusinessType.RESTAURANT.name());
        resButton.setActionCommand("rest");

        businessTypeGroup = new ButtonGroup();
        businessTypeGroup.add(cafeButton);
        businessTypeGroup.add(groceryButton);
        businessTypeGroup.add(retailButton);
        businessTypeGroup.add(resButton);

        businessButtons.add(cafeButton);
        businessButtons.add(groceryButton);
        businessButtons.add(retailButton);
        businessButtons.add(resButton);

        add(businessButtons);
    }

    //MODIFY: this:
    //EFFECTS: creates text fields for username, first name, last name, and location
    public void generateTextFields() {
        JLabel usernameLabel = new JLabel("Username:");
        username = new TextField();
        username.setPreferredSize(new Dimension(200, 30));
        add(usernameLabel);
        add(username);

        add(Box.createRigidArea(new Dimension(400, 25)));

        generateNameTextFields();

        JLabel businessNameLabel = new JLabel("Business Name:");
        businessName = new TextField();
        businessName.setPreferredSize(new Dimension(200, 30));
        add(businessNameLabel);
        add(businessName);

        add(Box.createRigidArea(new Dimension(400, 25)));

        JLabel locationLabel = new JLabel("Location:");
        location = new TextField();
        location.setPreferredSize(new Dimension(200, 30));
        add(locationLabel);
        add(location);

        add(Box.createRigidArea(new Dimension(400, 25)));
    }

    //MODIFY: this:
    //EFFECTS: creates text fields for name
    public void generateNameTextFields() {
        JLabel firstNameLabel = new JLabel("First Name:");
        firstName = new TextField();
        firstName.setPreferredSize(new Dimension(200, 30));
        add(firstNameLabel);
        add(firstName);

        add(Box.createRigidArea(new Dimension(400, 25)));

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastname = new TextField();
        lastname.setPreferredSize(new Dimension(200, 30));
        add(lastNameLabel);
        add(lastname);

        add(Box.createRigidArea(new Dimension(400, 25)));
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click & create account based on input

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        User createdUser;
        BusinessType type;

        if (e.getSource() == submitButton) {
            String usernameData = username.getText();
            String firstNameData = firstName.getText();
            String lastNameData = lastname.getText();
            String locationData = location.getText();
            String businessNameData = businessName.getText();
            if (group.getSelection().getActionCommand().equals("Personal")) {
                createdUser = new PersonalUser(usernameData, locationData, firstNameData, lastNameData);
            } else {
                type = findType();
                createdUser = new BusinessUser(usernameData, locationData, businessNameData, type);
            }

            this.app.setUser(createdUser);

            this.app.setStatus("It's great for you to join us from "
                    + locationData + "!" + " Your username $" + usernameData + " is ready to be used! Woooot!");

            cl.show(this.app.getContainer(), Pages.MENU.name());
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
            default:
                type = BusinessType.OTHER;
        }

        return type;
    }
}
