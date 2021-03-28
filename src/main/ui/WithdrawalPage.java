package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class WithdrawalPage extends JPanel implements ActionListener, ItemListener {
    JPanel app;
    JButton confirmButton = new JButton("Confirm Withdraw");

    public WithdrawalPage(JPanel app) {
        this.app = app;
        JPanel creditCardPane = new JPanel();

        String[] creditCards = {"Placeholder Card 1", "Placeholder Card 2"};

        JComboBox cb = new JComboBox(creditCards);

        cb.setEditable(false);
        cb.addItemListener(this);

        creditCardPane.add(cb);

        TextField amountField = new TextField();
        amountField.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Withdraw Amount:");

        confirmButton.addActionListener(this);

        add(creditCardPane);
        add(amountLabel);
        add(amountField);
        add(confirmButton);
        add(new ReturnToMenuButton(this.app));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getLayout());
        if (e.getSource() == confirmButton) {
            cl.show(this.app, Pages.MENU.name());
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
