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

//class that creates purchase page that allows user to send money for purchase
public class PurchasePage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Purchase");
    TextField sendAmount;
    TextField recipientUsername;

    //MODIFY: this
    //EFFECTS: creates page that displays and adds a table to app with completed, pending, and failed transactions
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

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: creates page that displays credit cards and allows user to input what amount they want to purchase
    public void createPage() {
        new PageTitle(this, "Make Purchase");

        add(Box.createRigidArea(new Dimension(400, 25)));

        JLabel usernameLabel = new JLabel("Recipient Username:");
        recipientUsername = new TextField();
        recipientUsername.setPreferredSize(new Dimension(200, 30));

        add(usernameLabel);
        add(recipientUsername);

        add(Box.createRigidArea(new Dimension(400, 25)));

        JLabel amountLabel = new JLabel("Purchase Amount:");
        sendAmount = new TextField();
        sendAmount.setPreferredSize(new Dimension(200, 30));

        add(amountLabel);
        add(sendAmount);

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(confirmButton);

        add(new ReturnToMenuButton(app.getContainer()));
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
