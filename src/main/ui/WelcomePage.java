package ui;

import model.Account;
import persistence.JsonAccountReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class WelcomePage extends JPanel implements ActionListener {
    MainApp app;
    JButton createAccButton = new JButton("Create Account");
    JButton loginButton = new JButton("Login");

    // EFFECTS: constructor that creates welcome page
    public WelcomePage(MainApp app) {
        this.app = app;
        add(displayWelcomeLabel());

        createAccButton.addActionListener(this);
        loginButton.addActionListener(this);

        add(createAccButton);
        add(loginButton);
    }

    //MODIFY: this
    //EFFECTS: displays welcome label as first screen that greets users
    public JLabel displayWelcomeLabel() {
        JLabel welcomeLabel = new JLabel();

        welcomeLabel.setText("Welcome to Cash App '98! Our mission is to create an inclusive economy"
                + " by helping you send, receive, and spend money easier");

        return welcomeLabel;
    }
    //MODIFY: this
    //EFFECTS: loads account from JSON file if it exists, create account with given input for create Account Button

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        JsonAccountReader jsonAccountReader = this.app.getJsonReader();

        if (e.getSource() == createAccButton) {
            cl.show(this.app.getContainer(), Pages.CREATE_ACCOUNT.name());

        } else if (e.getSource() == loginButton) {
            try {
                Account account = jsonAccountReader.read();
                this.app.setUser(account.getUser());
                this.app.getUser().setAccount(account);
                //TODO: Add success banner on top of menu page
                JLabel successLabel = new JLabel("Welcome back " + account.getUser().getUsername()
                        + "! Your info was successfully loaded!");
                cl.show(this.app.getContainer(), Pages.MENU.name());

            } catch (IOException exception) {
                //TODO: Add error banner on top of create account page
                JLabel noAccountLabel = new JLabel("Oops! We were unable to read from your file!");
                cl.show(this.app.getContainer(), Pages.CREATE_ACCOUNT.name());
            }
        }
    }
}
