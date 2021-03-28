package ui;

import model.Account;
import model.BusinessUser;
import model.Transaction;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PurchasePage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Purchase");
    TextField sendAmount;
    TextField recipientUsername;

    //EFFECTS: constructor to create purchase page that accepts user input for recipient and amount
    public PurchasePage(MainApp app) {
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
    }

    public void createPage() {
        recipientUsername = new TextField();
        recipientUsername.setPreferredSize(new Dimension(100, 30));
        JLabel usernameLabel = new JLabel("Recipient Username:");

        sendAmount = new TextField();
        sendAmount.setPreferredSize(new Dimension(100, 30));
        JLabel amountLabel = new JLabel("Purchase Amount:");

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
            checkBalance();
            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    //MODIFY: app user balance
    //EFFECTS: checks if user has enough funds to make purchase, decrements balance if true, error message if false
    public void checkBalance() {
        User recipientUser = new BusinessUser(recipientUsername.getText(), "Seattle", "Amazon",
                BusinessUser.BusinessType.RETAILER);
        int sendAmtData = Integer.valueOf(sendAmount.getText());
        int currentBalance = (int) this.app.getUser().getAccount().getBalance();
        Account recipientAccount = new Account(recipientUser, 5000);
        Account senderAccount = this.app.getUser().getAccount();

        if (currentBalance - sendAmtData >= 0) {
            this.app.setStatus("Congrats! Your purchase went through!");
            this.app.getUser().getAccount().decrementBalance(sendAmtData);

            Transaction newTransaction = new Transaction(recipientAccount, senderAccount, sendAmtData,
                    Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
            this.app.getUser().getAccount().addToTransactions(newTransaction);

        } else {
            this.app.setStatus("Oops...looks like you don't have enough funds");
        }
    }
}
