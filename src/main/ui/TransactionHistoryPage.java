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
public class TransactionHistoryPage extends JPanel implements ActionListener, Page {
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

        setOpaque(false);
    }

    //MODIFY: this
    //EFFECTS: creates page that displays and adds a table to app with completed, pending, and failed transactions
    @Override
    public void createPage() {
        ImageIcon book = this.app.getEmoji("book", 50, 50);
        new PageTitle(this, "Transactions", book);

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

            displayTransactions(columnNames, data, completedTransactions, 0);
            displayTransactions(columnNames, data, pendingTransactions, completedTransactions.size());
            displayTransactions(columnNames, data, failedTransactions, pendingTransactions.size());
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        add(scrollPane);

        add(Box.createRigidArea(new Dimension(400, 25)));

        add(new ReturnToMenuButton(this.app.getContainer()));
    }

    //EFFECTS: prints all transactions with give parameters
    public void displayTransactions(String[] columnNames, Object[][] data, List<Transaction> transactions, int offset) {
        for (int i = 0; i < transactions.size(); i++) {
            String[] transactionArray = {
                    String.valueOf(transactions.get(i).getDate()),
                    transactions.get(i).getRecipientUsername(),
                    transactions.get(i).getSenderUsername(),
                    String.valueOf(transactions.get(i).getAmount()),
                    transactions.get(i).getStatus().name(),
            };
            data[i + offset] = transactionArray;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
