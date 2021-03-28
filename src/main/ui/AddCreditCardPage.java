package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCreditCardPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Add Credit Card");
    TextField typeField;
    TextField numField;
    TextField expiryYearField;
    TextField expiryMonthField;

    //EFFECTS: constructor to add a new credit card to account
    public AddCreditCardPage(MainApp app) {
        this.app = app;
        setLayout(new GridLayout(0, 2, 5, 5));

        initializeFields();
        JLabel typeLabel = new JLabel("Credit Card Type:");

        JLabel numLabel = new JLabel("Credit Card Number:");

        JLabel expiryYearLabel = new JLabel("Credit Card Expiry Year:");

        JLabel expiryMonthLabel = new JLabel("Credit Card Expiry Month:");
        confirmButton.setActionCommand("CREDIT_CARD_ADDED");
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

    public void initializeFields() {
        typeField = new TextField();
        typeField.setPreferredSize(new Dimension(100, 5));
        numField = new TextField();
        numField.setPreferredSize(new Dimension(100, 5));
        expiryYearField = new TextField();
        expiryYearField.setPreferredSize(new Dimension(100, 30));
        expiryMonthField = new TextField();
        expiryMonthField.setPreferredSize(new Dimension(100, 30));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == confirmButton) {
            String typeData = typeField.getText();
            int numData = Integer.valueOf(numField.getText());
            int monthData = Integer.valueOf(expiryMonthField.getText());
            int yearData = Integer.valueOf(expiryYearField.getText());
            CreditCard card = new CreditCard(typeData, numData, yearData, monthData);
            this.app.getUser().getAccount().addCreditCard(card);
            System.out.println(card);
            System.out.println(this.app.getUser().getAccount().getCreditCards());
            this.app.revalidate();
            this.app.repaint();
            //TODO: add success message
            cl.show(this.app.getContainer(), Pages.CREDIT_CARD.name());
        }
    }
}