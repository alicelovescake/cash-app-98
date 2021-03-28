package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMoneyPage extends JPanel implements ActionListener {
    JPanel app;
    JButton confirmButton = new JButton("Confirm Send");

    //EFFECTS: constructor to create send money page that accepts user input for recipient and amount
    public SendMoneyPage(JPanel app) {
        this.app = app;

        confirmButton.addActionListener(this);

        TextField recipientUsername = new TextField();
        recipientUsername.setSize(new Dimension(100, 30));
        JLabel usernameLabel = new JLabel("Recipient Username:");

        TextField sendAmount = new TextField();
        sendAmount.setSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Send Amount:");

        add(usernameLabel);
        add(recipientUsername);
        add(amountLabel);
        add(sendAmount);
        add(confirmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());
        if (e.getSource() == confirmButton) {
            cl.show(this.app, Pages.MENU.name());
        }
    }
}
