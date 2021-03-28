package ui;

import model.Account;
import model.BusinessUser;
import model.Transaction;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestMoneyPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Request");
    TextField recipientUsername;
    TextField requestAmount;

    //EFFECTS: constructor to create request money page that accepts user input for recipient and amount
    public RequestMoneyPage(MainApp app) {
        this.app = app;

        confirmButton.addActionListener(this);

        recipientUsername = new TextField();
        recipientUsername.setPreferredSize(new Dimension(100, 30));
        JLabel usernameLabel = new JLabel("Recipient Username:");

        requestAmount = new TextField();
        requestAmount.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Request Amount:");

        add(usernameLabel);
        add(recipientUsername);
        add(amountLabel);
        add(requestAmount);
        add(confirmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());
        User recipientUser = new BusinessUser(recipientUsername.getText(), "Seattle", "Amazon",
                BusinessUser.BusinessType.RETAILER);

        Account senderAccount = this.app.getUser().getAccount();
        int requestAmtData = Integer.valueOf(requestAmount.getText());
        Account recipientAccount = new Account(recipientUser, 5000);

        if (e.getSource() == confirmButton) {
            Transaction newTransaction = new Transaction(recipientAccount, senderAccount, requestAmtData,
                    Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
            this.app.getUser().getAccount().addToTransactions(newTransaction);

            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }
}
