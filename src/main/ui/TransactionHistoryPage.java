package ui;

import model.Transaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionHistoryPage extends JPanel implements ActionListener {
    JPanel app;

    //EFFECTS: constructor to create send money page that displays transaction history
    public TransactionHistoryPage(JPanel app) {
        this.app = app;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
