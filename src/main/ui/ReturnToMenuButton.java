package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//class that creates a button that when pressed, returns to main menu
public class ReturnToMenuButton extends JButton implements ActionListener {
    JPanel app;

    //EFFECTS: constructor that sets text of button and adds action listener
    public ReturnToMenuButton(JPanel app) {
        this.app = app;

        setText("Return to Menu");

        addActionListener(this);
    }

    // EFFECTS: redirects app to main menu when listener detects button click
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());

        if (e.getSource() == this) {
            cl.show(this.app, Pages.MENU.name());
        }

    }
}
