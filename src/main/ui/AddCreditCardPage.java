package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCreditCardPage extends JPanel implements ActionListener {
    JPanel app;
    JButton confirmButton = new JButton("Confirm Add Credit Card");
    TextField typeField;
    TextField numField;
    TextField expiryYearField;
    TextField expiryMonthField;

    //EFFECTS: constructor to add a new credit card to account
    public AddCreditCardPage(JPanel app) {
        this.app = app;
        setLayout(new GridLayout(0, 2, 5, 5));

        typeField = new TextField();
        typeField.setSize(new Dimension(100, 5));
        JLabel typeLabel = new JLabel("Credit Card Type:");

        numField = new TextField();
        numField.setSize(new Dimension(100, 5));
        JLabel numLabel = new JLabel("Credit Card Number:");

        expiryYearField = new TextField();
        expiryYearField.setSize(new Dimension(100, 30));
        JLabel expiryYearLabel = new JLabel("Credit Card Expiry Year:");

        expiryMonthField = new TextField();
        expiryMonthField.setSize(new Dimension(100, 30));
        JLabel expiryMonthLabel = new JLabel("Credit Card Expiry Month:");
        confirmButton.addActionListener(this);

        add(typeLabel);
        add(typeField);
        add(numLabel);
        add(numField);
        add(expiryYearLabel);
        add(expiryYearField);
        add(expiryMonthLabel);
        add(expiryMonthField);
        add(confirmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());

        if (e.getSource() == confirmButton) {
            cl.show(this.app, Pages.CREDIT_CARD.name());
        }
    }
}
