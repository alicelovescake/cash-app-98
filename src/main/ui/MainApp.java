package ui;

import model.User;
import persistence.JsonAccountReader;
import persistence.JsonAccountWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//CITATION: Structure of this GUI is modeled after SimpleDrawingPlayer
//          URL: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
public class MainApp extends JFrame implements ActionListener {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 800;
    private static final String JSON_ACCOUNT_STORE = "./data/account.json";
    private static JsonAccountReader jsonAccountReader = new JsonAccountReader(JSON_ACCOUNT_STORE);
    private static JsonAccountWriter jsonAccountWriter = new JsonAccountWriter(JSON_ACCOUNT_STORE);
    private static User user;

    private JPanel app;

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

    public static User getUser() {
        return user;
    }

    //Setters
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
        app = new JPanel(new CardLayout());
        JPanel welcomePage = new WelcomePage(app);
        JPanel createAccountPage = new AccountPage(app);
        JPanel createMenuPage = new MenuPage(app);
        JPanel addCashPage = new DepositPage(app);
        JPanel cashOutPage = new WithdrawalPage(app);
        JPanel createPurchasePage = new PurchasePage(app);

        app.add(welcomePage, Pages.WELCOME.name());
        app.add(createAccountPage, Pages.CREATE_ACCOUNT.name());
        app.add(createMenuPage, Pages.MENU.name());
        app.add(addCashPage, Pages.DEPOSIT.name());
        app.add(cashOutPage, Pages.WITHDRAW.name());
        app.add(createPurchasePage, Pages.PURCHASE.name());
        initializeMoreAppPages();
        add(app, BorderLayout.CENTER);
    }

    //MODIFY: this
    //EFFECTS: Creates app layout and adds all pages to app
    public void initializeMoreAppPages() {
        JPanel requestMoneyPage = new RequestMoneyPage(app);
        JPanel sendMoneyPage = new SendMoneyPage(app);
        JPanel creditCardPage = new CreditCardPage(app);
        JPanel addCreditCard = new AddCreditCardPage(app);
        JPanel transactionHistoryPage = new TransactionHistoryPage(app);

        app.add(requestMoneyPage, Pages.REQUEST.name());
        app.add(sendMoneyPage, Pages.SEND.name());
        app.add(creditCardPage, Pages.CREDIT_CARD.name());
        app.add(addCreditCard, Pages.ADD_CREDIT_CARD.name());
        app.add(transactionHistoryPage, Pages.TRANSACTION.name());
    }

    // Starts CashApp
    public static void main(String[] args) {
        new MainApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        String command = e.getActionCommand();
//        CardLayout cl = (CardLayout) (app.getLayout());

    }


}
