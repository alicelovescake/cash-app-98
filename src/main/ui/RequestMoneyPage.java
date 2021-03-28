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

//class that creates request money page that allows user to request funds from another user
public class RequestMoneyPage extends JPanel implements ActionListener {
    MainApp app;
    JButton confirmButton = new JButton("Confirm Request");
    TextField recipientUsername;
    TextField requestAmount;

    //Effects: constructor that adds component & action listener to update and revalidate page when component changes.
    public RequestMoneyPage(MainApp app) {
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
    //EFFECTS: creates page that accepts user input for request amount and recipient. Adds to app.
    public void createPage() {
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
    //MODIFY: this
    // EFFECTS: action listener that listens to confirm button click, creates/add new transaction, sets success message &
    // redirects to main menu

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
                    Transaction.Type.REQUEST, Transaction.Status.PENDING);
            this.app.getUser().getAccount().addToTransactions(newTransaction);
            this.app.setStatus("Success! Your request went through!");

            cl.show(this.app.getContainer(), Pages.MENU.name());
        }
    }
}
