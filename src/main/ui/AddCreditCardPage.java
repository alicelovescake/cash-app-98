package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

//class that creates page to allow user to add new credit card
public class AddCreditCardPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Add Credit Card");
    TextField typeField;
    TextField numField;
    TextField expiryYearField;
    TextField expiryMonthField;

    //Effects: constructor that create page & adds component & action listener to update and revalidate page
    // when component changes.
    public AddCreditCardPage(MainApp app) {
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

        confirmButton.addActionListener(this);

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: create page to gather user input for credit card info and adds it to panel
    public void createPage() {
        new PageTitle(this, "Add Credit Card");

        initializeFields();

        JLabel typeLabel = new JLabel("Credit Card Type:");

        JLabel numLabel = new JLabel("Credit Card Number:");

        JLabel expiryYearLabel = new JLabel("Credit Card Expiry Year:");

        JLabel expiryMonthLabel = new JLabel("Credit Card Expiry Month:");
        confirmButton.setActionCommand("CREDIT_CARD_ADDED");

        add(typeLabel);
        add(typeField);

        add(numLabel);
        add(numField);

        add(expiryYearLabel);
        add(expiryYearField);

        add(expiryMonthLabel);
        add(expiryMonthField);

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(confirmButton);

        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    // Modify: this
    //EFFECTS: initializes new text fields and sets preferred dimensions
    public void initializeFields() {
        typeField = new TextField();
        typeField.setPreferredSize(new Dimension(200, 30));

        numField = new TextField();
        numField.setPreferredSize(new Dimension(200, 30));

        expiryYearField = new TextField();
        expiryYearField.setPreferredSize(new Dimension(200, 30));

        expiryMonthField = new TextField();
        expiryMonthField.setPreferredSize(new Dimension(200, 30));
    }

    //MODIFY: this
    //EFFECTS: implements action listener method to switch pages based on button click & create new credit card to add
    //to account. If card is valid, sets success status message, else error msg.
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

            if (card.getIsValid()) {
                this.app.setStatus("Congrats! Your card was successfully added");
            } else {
                this.app.setStatus("Sorry! Seems like your card was expired. Try again.");
            }

            cl.show(this.app.getContainer(), Pages.CREDIT_CARD.name());
        }
    }
}
