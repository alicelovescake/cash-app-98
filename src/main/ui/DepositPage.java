package ui;

import model.CreditCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DepositPage extends JPanel implements ActionListener, ItemListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Deposit");
    JButton addCreditCardBtn = new JButton("Add card");
    JLabel missingCardLabel = new JLabel("Oops! Looks like you don't have a card on file to deposit");
    JLabel successLabel;
    TextField amountField;

    public DepositPage(MainApp app) {
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
                JLabel amountLabel = new JLabel("Deposit Amount:");

                add(amountLabel);
                add(amountField);
                add(confirmButton);
            }
        }

        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    public void displayCreditCards() {
        int creditCardCount = this.app.getUser().getAccount().getCreditCards().size();

        JPanel creditCardPane = new JPanel();

        List<CreditCard> userCards = this.app.getUser().getAccount().getCreditCards();
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
            String amountData = amountField.getText();

            int depositAmt = Integer.valueOf(amountData);
            this.app.getUser().getAccount().incrementBalance(depositAmt);

            this.app.setStatus("Success! We've deposited $" + depositAmt + " into your account.");

            cl.show(this.app.getContainer(), Pages.MENU.name());
        } else if (e.getSource() == addCreditCardBtn) {
            cl.show(this.app.getContainer(), Pages.ADD_CREDIT_CARD.name());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
