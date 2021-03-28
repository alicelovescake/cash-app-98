package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnToMenuButton extends JButton implements ActionListener {
    JPanel app;

    public ReturnToMenuButton(JPanel app) {
        this.app = app;
        this.setText("Return to Menu");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());

        if (e.getSource() == this) {
            cl.show(this.app, Pages.MENU.name());
        }

    }
}
