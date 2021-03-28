package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

// This class creates a page to display transaction history of the user
public class TransactionHistoryPage extends JPanel implements ActionListener {
    MainApp app;

    private List<Transaction> completedTransactions;
    private List<Transaction> pendingTransactions;
    private List<Transaction> failedTransactions;

    //Effects: constructor that create page & adds component & action listener to update and revalidate page
    // when component changes.
    public TransactionHistoryPage(MainApp app) {
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
    }
    //MODIFY: this
    //EFFECTS: creates page that displays and adds a table to app with completed, pending, and failed transactions

    public void createPage() {
        setLayout(new GridLayout(0, 1));

        String[] columnNames = {"DATE", "RECIPIENT", "SENDER", "AMOUNT", "STATUS"};
        Object[][] data = new Object[0][columnNames.length];

        if (this.app.getUser() != null) {
            completedTransactions = this.app.getUser().getAccount().getCompletedTransactions();
            pendingTransactions = this.app.getUser().getAccount().getPendingTransactions();
            failedTransactions = this.app.getUser().getAccount().getFailedTransactions();

            int totalTransactions = completedTransactions.size()
                    + pendingTransactions.size()
                    + failedTransactions.size();

            data = new Object[totalTransactions][columnNames.length];

            displayTransactions(columnNames, data, completedTransactions);
            displayTransactions(columnNames, data, pendingTransactions);
            displayTransactions(columnNames, data, failedTransactions);
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);
        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    //EFFECTS: prints all transactions with give parameters
    public void displayTransactions(String[] columnNames, Object[][] data, List<Transaction> transactions) {
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
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
