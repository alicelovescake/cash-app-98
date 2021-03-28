package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class WithdrawalPage extends JPanel implements ActionListener, ItemListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Withdraw");
    int numCreditCardsAvailable;
    TextField amountField;

    public WithdrawalPage(MainApp app) {
        this.app = app;
        JPanel creditCardPane = new JPanel();

        amountField = new TextField();
        amountField.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Withdraw Amount:");

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
            int withdrawAmt = Integer.valueOf(amountData);
            this.app.getUser().getAccount().decrementBalance(withdrawAmt);

            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
