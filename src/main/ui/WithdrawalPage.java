package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//class that creates withdraw page that allows user to cash out of account
public class WithdrawalPage extends JPanel implements ActionListener, ItemListener, Page {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Withdraw");
    JButton addCreditCardBtn = new JButton("Add card");
    JLabel missingCardLabel = new JLabel("Oops! Looks like you don't have a card on file to withdraw");
    TextField amountField;

    //Effects: constructor that create page & adds component & action listener to update and revalidate page
    // when component changes.
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

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: creates page that displays credit cards and allows user to input what amount they want to withdraw
    @Override
    public void createPage() {
        new PageTitle(this, "Withdraw");

        if (this.app.getUser() != null) {
            int creditCardCount = this.app.getUser().getAccount().getCreditCards().size();

            if (creditCardCount == 0) {
                add(missingCardLabel);
                add(addCreditCardBtn);
            } else {
                displayCreditCards();
                add(Box.createRigidArea(new Dimension(400, 25)));

                JLabel amountLabel = new JLabel("Withdraw Amount:");
                amountField = new TextField();
                amountField.setPreferredSize(new Dimension(200, 30));

                add(amountLabel);
                add(amountField);

                add(Box.createRigidArea(new Dimension(400, 25)));

                add(confirmButton);
            }
        }

        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    //Modify: this
    //Effects: displays all credit cards on account and adds this panel to app
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
        creditCardPane.setOpaque(false);

        add(creditCardPane);
    }

    // EFFECTS: action listener that listens to confirm button click, checks balance and redirects to main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        if (e.getSource() == confirmButton) {
            checkBalance();

            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    //MODIFY: this
    //EFFECTS: checks balance of user account, if sufficient funds, adjusts balance and success msg, else error msg.
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
