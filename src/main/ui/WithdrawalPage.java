package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WithdrawalPage extends JPanel implements ActionListener, ItemListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Withdraw");
    JButton addCreditCardBtn = new JButton("Add card");
    JLabel missingCardLabel = new JLabel("Oops! Looks like you don't have a card on file to withdraw");
    TextField amountField;

    public WithdrawalPage(MainApp app) {
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
        addCreditCardBtn.addActionListener(this);
    }

    public void createPage() {
        if (this.app.getUser() != null) {

            int creditCardCount = this.app.getUser().getAccount().getCreditCards().size();

            if (creditCardCount == 0) {
                add(missingCardLabel);
                add(addCreditCardBtn);
            } else {
                displayCreditCards();

                amountField = new TextField();
                amountField.setPreferredSize(new Dimension(100, 30));
                JLabel amountLabel = new JLabel("Withdraw Amount:");

                add(amountLabel);
                add(amountField);
                add(confirmButton);
            }
        }

        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    public void displayCreditCards() {
        JPanel creditCardPane = new JPanel();

        int creditCardCount = this.app.getUser().getAccount().getCreditCards().size();

        java.util.List<CreditCard> userCards = this.app.getUser().getAccount().getCreditCards();
        String[] creditCards = new String[creditCardCount];

        for (int i = 0; i < creditCardCount; i++) {
            creditCards[i] = Long.toString(userCards.get(i).getCardNumber());
        }

        JComboBox cb = new JComboBox(creditCards);

        cb.setEditable(false);
        cb.addItemListener(this);

        creditCardPane.add(cb);

        add(creditCardPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        if (e.getSource() == confirmButton) {
            checkBalance();

            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    public void checkBalance() {
        String amountData = amountField.getText();
        int withdrawAmt = Integer.valueOf(amountData);
        int currentBalance = (int) this.app.getUser().getAccount().getBalance();

        if (withdrawAmt <= currentBalance) {
            this.app.getUser().getAccount().decrementBalance(withdrawAmt);
            this.app.setStatus("Success! Your withdraw of $" + withdrawAmt + " was completed");
        } else {
            this.app.setStatus("Sorry! Your balance is insufficient for this withdraw");
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
