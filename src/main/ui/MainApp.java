package ui;

import model.User;
import persistence.JsonAccountReader;
import persistence.JsonAccountWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//CITATION: Structure of this GUI is modeled after SimpleDrawingPlayer
//          URL: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
//Main class that starts up cash app when new instance is created
public class MainApp extends JFrame implements ActionListener {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 800;
    private static final String JSON_ACCOUNT_STORE = "./data/account.json";
    private static JsonAccountReader jsonAccountReader = new JsonAccountReader(JSON_ACCOUNT_STORE);
    private static JsonAccountWriter jsonAccountWriter = new JsonAccountWriter(JSON_ACCOUNT_STORE);
    private static User user;
    private JLabel status = new JLabel();
    private JPanel container;

    public MainApp() {
        super("Cash App '98");
        initializeGraphics();
    }

    //Getters: gets values from this
    public static JsonAccountReader getJsonReader() {
        return jsonAccountReader;
    }

    public static JsonAccountWriter getJsonWriter() {
        return jsonAccountWriter;
    }

    public static String getAccountStore() {
        return JSON_ACCOUNT_STORE;
    }

    public static User getUser() {
        return user;
    }

    public JPanel getContainer() {
        return container;
    }

    public JLabel getStatus() {
        return status;
    }

    //Setters
    public void setStatus(String msg) {
        status.setText(msg);
    }

    public static void setUser(User user) {
        MainApp.user = user;
    }


    //MODIFY: this
    //EFFECTS: draws on JFrame window for CashApp and populate with card layout with different options
    public void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);

        getContentPane().setBackground(new Color(228, 233, 216));
        initializeAppPages();
        setVisible(true);
    }

    //MODIFY: this
    //EFFECTS: Creates app layout and adds all pages to app
    public void initializeAppPages() {
        container = new JPanel(new CardLayout());
        container.setOpaque(false);

        try {
            JPanel welcomePage = new WelcomePage(this);

            JPanel createAccountPage = new AccountPage(this);
            JPanel createMenuPage = new MenuPage(this);
            JPanel addCashPage = new DepositPage(this);
            JPanel cashOutPage = new WithdrawalPage(this);
            JPanel createPurchasePage = new PurchasePage(this);

            container.add(welcomePage, Pages.WELCOME.name());
            container.add(createAccountPage, Pages.CREATE_ACCOUNT.name());
            container.add(createMenuPage, Pages.MENU.name());
            container.add(addCashPage, Pages.DEPOSIT.name());
            container.add(cashOutPage, Pages.WITHDRAW.name());
            container.add(createPurchasePage, Pages.PURCHASE.name());

            initializeMoreAppPages();

            add(container, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //MODIFY: this
    //EFFECTS: Creates app layout and adds all pages to app
    public void initializeMoreAppPages() {
        JPanel requestMoneyPage = new RequestMoneyPage(this);
        JPanel sendMoneyPage = new SendMoneyPage(this);
        JPanel creditCardPage = new CreditCardPage(this);
        JPanel addCreditCard = new AddCreditCardPage(this);
        JPanel transactionHistoryPage = new TransactionHistoryPage(this);
        JPanel addBoostPage = new AddBoostPage(this);

        container.add(requestMoneyPage, Pages.REQUEST.name());
        container.add(sendMoneyPage, Pages.SEND.name());
        container.add(creditCardPage, Pages.CREDIT_CARD.name());
        container.add(addCreditCard, Pages.ADD_CREDIT_CARD.name());
        container.add(transactionHistoryPage, Pages.TRANSACTION.name());
        container.add(addBoostPage, Pages.ADD_BOOST.name());
    }


    // Starts CashApp
    public static void main(String[] args) {
        new MainApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
