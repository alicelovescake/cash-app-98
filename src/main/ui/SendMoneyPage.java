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

//class that creates send money page that allows user to send funds to another user
public class SendMoneyPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Send");
    TextField recipientUsername;
    TextField sendAmount;

    //Effects: constructor that create page & adds component & action listener to update and revalidate page
    // when component changes.
    public SendMoneyPage(MainApp app) {
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

    //MODIFY: this
    //EFFECTS: creates page that allows user to input what amount they want to send
    public void createPage() {
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


    // EFFECTS: action listener that listens to confirm button click, checks balance & redirects to main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout) (this.app.getContainer().getLayout());

        if (e.getSource() == confirmButton) {
            checkBalance();
            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }

    //MODIFY: app user balance
    //EFFECTS: checks if user has enough funds to send money, decrements balance if true, error message if false
    public void checkBalance() {
        int sendAmtData = Integer.valueOf(sendAmount.getText());
        int currentBalance = (int) this.app.getUser().getAccount().getBalance();
        User recipientUser = new BusinessUser(recipientUsername.getText(), "Seattle", "Amazon",
                BusinessUser.BusinessType.RETAILER);

        Account recipientAccount = new Account(recipientUser, 5000);
        Account senderAccount = this.app.getUser().getAccount();

        if (currentBalance - sendAmtData >= 0) {
            this.app.setStatus("Congrats! $" + sendAmtData + " was sent to " + recipientUsername.getText());
            this.app.getUser().getAccount().decrementBalance(sendAmtData);

            Transaction newTransaction = new Transaction(recipientAccount, senderAccount, sendAmtData,
                    Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
            this.app.getUser().getAccount().addToTransactions(newTransaction);
        } else {
            this.app.setStatus("Oops...looks like you don't have enough funds");
        }
    }
}
