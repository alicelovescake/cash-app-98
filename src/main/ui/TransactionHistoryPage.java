package ui;

import model.Account;
import model.PersonalUser;
import model.Transaction;
import model.User;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryPage extends JPanel implements ActionListener {
    MainApp app;

    //    private User testUserSender = new PersonalUser("$alicelovescake", "Vancouver",
//            "Alice", "Zhao");
//    private User testUserReceiver = new PersonalUser("$boblovespizza", "Vancouver",
//            "Bob", "Marley");
//    private Account testSender = new Account(testUserSender, 100.0);
//    private Account testReceiver = new Account(testUserReceiver, 500.0);
//    private Transaction testTransaction = new Transaction(testReceiver, testSender, 100.0,
//            Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
//    private Transaction testTransaction2 = new Transaction(testSender, testReceiver, 50.0,
//            Transaction.Type.EXCHANGE, Transaction.Status.PENDING);
    private List<Transaction> completedTransactions;
    private List<Transaction> pendingTransactions;
    private List<Transaction> failedTransactions;

    //EFFECTS: constructor to create send money page that displays transaction history
    public TransactionHistoryPage(MainApp app) {
        this.app = app;
//        transactions.add(testTransaction);
//        transactions.add(testTransaction2);

        if (this.app.getUser() != null) {
            completedTransactions = this.app.getUser().getAccount().getCompletedTransactions();
            pendingTransactions = this.app.getUser().getAccount().getPendingTransactions();
            failedTransactions = this.app.getUser().getAccount().getFailedTransactions();
        }

        setLayout(new GridLayout(0, 1));

        String[] columnNames = {"DATE", "RECIPIENT", "SENDER", "AMOUNT", "STATUS"};
        displayTransactions(columnNames, completedTransactions);
        displayTransactions(columnNames, pendingTransactions);
        displayTransactions(columnNames, failedTransactions);


        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    //EFFECTS: prints all transactions with give parameters
    public Object[][] displayTransactions(String[] columnNames, List<Transaction> transactions) {
        Object[][] data = new Object[transactions.size()][columnNames.length];
        for (int i = 0; i < transactions.size(); i++) {
            String[] transactionArray = {
                    String.valueOf(transactions.get(i).getDate()),
                    transactions.get(i).getRecipientUsername(),
                    transactions.get(i).getSenderUsername(),
                    String.valueOf(transactions.get(i).getAmount()),
                    transactions.get(i).getStatus().name(),
            };
            data[i] = transactionArray;
        }
        return data;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
