package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class DepositPage extends JPanel implements ActionListener, ItemListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Deposit");
    JButton addCreditCardBtn = new JButton("Add card?");
    JLabel missingCardLabel = new JLabel("Oops! Looks like you don't have a card on file to deposit");
    JLabel successLabel;
    int numCreditCardsAvailable;
    TextField amountField;

    public DepositPage(MainApp app) {
        this.app = app;
        numCreditCardsAvailable();
        JPanel creditCardPane = new JPanel();

        if (numCreditCardsAvailable == 0) {
            add(missingCardLabel);
            add(addCreditCardBtn);
            addCreditCardBtn.addActionListener(this);
        } else {
            addCreditCardDisplay(creditCardPane);
        }

        amountField = new TextField();
        amountField.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Deposit Amount:");

        confirmButton.addActionListener(this);

        add(creditCardPane);
        add(amountLabel);
        add(amountField);
        add(confirmButton);
        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    //EFFECTS: determines if user has any credit cards on file
    public int numCreditCardsAvailable() {
        this.numCreditCardsAvailable = this.app.getUser() == null ? 0 : this.app.getUser().getAccount()
                .getCreditCards().size();
        return numCreditCardsAvailable;
    }

    //EFFECTS: loops over credit cards available and displays in combo box
    public void addCreditCardDisplay(JPanel creditCardPane) {
        List userCards = (List) this.app.getUser().getAccount().getCreditCards();
        String[] creditCards = new String[numCreditCardsAvailable];

        for (int i = 0; i < numCreditCardsAvailable; i++) {
            creditCards[i] = userCards.getItem(i);
        }

        JComboBox cb = new JComboBox(creditCards);

        cb.setEditable(false);
        cb.addItemListener(this);

        creditCardPane.add(cb);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == confirmButton) {
            String amountData = amountField.getText();
            int depositAmt = Integer.valueOf(amountData);
            this.app.getUser().getAccount().incrementBalance(depositAmt);
            //TODO: display success message
            successLabel = new JLabel("We have deposited $" + depositAmt + " into your account.");

            cl.show(this.app.getContainer(), Pages.MENU.name());
        } else if (e.getSource() == addCreditCardBtn) {
            cl.show(this.app.getContainer(), Pages.ADD_CREDIT_CARD.name());
        }


    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
