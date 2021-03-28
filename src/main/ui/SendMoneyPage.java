package ui;

import model.Account;
import model.BusinessUser;
import model.Transaction;
import model.User;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SendMoneyPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Send");
    TextField recipientUsername;
    TextField sendAmount;

    //EFFECTS: constructor to create send money page that accepts user input for recipient and amount
    public SendMoneyPage(MainApp app) {
        this.app = app;

        confirmButton.addActionListener(this);

        recipientUsername = new TextField();
        recipientUsername.setPreferredSize(new Dimension(100, 30));
        JLabel usernameLabel = new JLabel("Recipient Username:");

        sendAmount = new TextField();
        sendAmount.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Send Amount:");

        add(usernameLabel);
        add(recipientUsername);
        add(amountLabel);
        add(sendAmount);
        add(confirmButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == confirmButton) {
            add(checkBalance());
            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    //MODIFY: app user balance
    //EFFECTS: checks if user has enough funds to send money, decrements balance if true, error message if false
    public JLabel checkBalance() {
        JLabel status;
        int sendAmtData = Integer.valueOf(sendAmount.getText());
        int currentBalance = (int) this.app.getUser().getAccount().getBalance();
        User recipientUser = new BusinessUser(recipientUsername.getText(), "Seattle", "Amazon",
                BusinessUser.BusinessType.RETAILER);

        Account recipientAccount = new Account(recipientUser, 5000);
        Account senderAccount = this.app.getUser().getAccount();

        if (currentBalance - sendAmtData >= 0) {
            status = new JLabel("Congrats! Your money was sent to " + recipientUsername.getText());
            this.app.getUser().getAccount().decrementBalance(sendAmtData);

            Transaction newTransaction = new Transaction(recipientAccount, senderAccount, sendAmtData,
                    Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
            this.app.getUser().getAccount().addToTransactions(newTransaction);
        } else {
            status = new JLabel("Oops...looks like you don't have enough funds");
        }

        return status;

    }
}
