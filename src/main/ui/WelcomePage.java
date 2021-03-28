package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JPanel implements ActionListener {
    JPanel app;
    JButton createAccButton = new JButton("Create Account");
    JButton loginButton = new JButton("Login");

    // EFFECTS: constructor that creates welcome page
    public WelcomePage(JPanel app) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());
        if (e.getSource() == createAccButton) {
            cl.show(this.app, Pages.CREATE_ACCOUNT.name());
        } else if (e.getSource() == loginButton) {
            cl.show(this.app, Pages.MENU.name());
        }
    }
}
