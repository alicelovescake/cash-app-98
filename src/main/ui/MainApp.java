package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

//CITATION: Structure of this GUI is modeled after SimpleDrawingPlayer
//          URL: https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
public class MainApp extends JFrame implements ActionListener, ItemListener {

    public static final int WIDTH = 450;
    public static final int HEIGHT = 800;
    static final String LOGIN_CARD = "login";
    static final String ACCOUNT_PAGE = "createAccount";
    static final String WELCOME_CARD = "welcomeUser";
    static final String MENU_CARD = "menuOptions";
    static final String CASH_OUT_CARD = "cashOut";
    static final String PURCHASE_CARD = "makePurchase";
    static final String CREDIT_CARDS_CARD = "updateCreditCards";
    static final String ADD_CREDIT_CARD_CARD = "addCreditCardPage";

    static final String CREATE_ACCOUNT = "submitAccountDetails";
    static final String CONFIRM_CASH_MOVEMENT = "confirmDeposit";
    static final String CONFIRM_PURCHASE = "confirmPurchase";
    static final String ADD_CREDIT_CARD = "addCreditCard";
    static final String REMOVE_CREDIT_CARD = "removeCreditCard";
    static final String CONFIRM_ADD_CREDIT_CARD = "confirmAddCreditCard";
    static final String SHOW_MENU = "return to menu";

    private JPanel app;


    public MainApp() {
        super("Cash App '98");
        initializeFields();
        initializeGraphics();
    }

    //MODIFY: this
    //EFFECTS: sets and instantiate
    public void initializeFields() {

    }

    //MODIFY: this
    //EFFECTS: draws on JFrame window for CashApp and populate with cardlayout with different options
    public void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);

        getContentPane().setBackground(new Color(228, 233, 216));
        initializeAppPages();
        setVisible(true);
    }


    //MODIFY: this
    //EFFECTS: Creates card layout to welcome user to create account or login
    public void initializeAppPages() {
        app = new JPanel(new CardLayout());

        JPanel welcomePage = new WelcomePage(app);
        JPanel createAccountPage = new AccountPage(app);
        JPanel createMenuPage = new MenuPage(app);
        JPanel addCashPage = new DepositPage(app);
        JPanel cashOutPage = new WithdrawalPage(app);
        JPanel createPurchasePage = new PurchasePage(app);
        JPanel requestMoneyPage = new RequestMoneyPage(app);
        JPanel sendMoneyPage = new SendMoneyPage(app);
        JPanel creditCardPage = new CreditCardPage(app);
        JPanel addCreditCard = new AddCreditCardPage(app);
        JPanel transactionHistoryPage = new TransactionHistoryPage(app);


        app.add(welcomePage, Pages.WELCOME.name());
        app.add(createAccountPage, Pages.CREATE_ACCOUNT.name());
        app.add(createMenuPage, Pages.MENU.name());
        app.add(addCashPage, Pages.DEPOSIT.name());
        app.add(cashOutPage, Pages.WITHDRAW.name());
        app.add(createPurchasePage, Pages.PURCHASE.name());
        app.add(requestMoneyPage, Pages.REQUEST.name());
        app.add(sendMoneyPage, Pages.SEND.name());

        app.add(creditCardPage, Pages.CREDIT_CARD.name());
        app.add(addCreditCard, Pages.ADD_CREDIT_CARD.name());
        app.add(transactionHistoryPage, Pages.TRANSACTION.name());
        add(app, BorderLayout.CENTER);
    }


    // Starts CashApp
    public static void main(String[] args) {
        new MainApp();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        CardLayout cl = (CardLayout) (app.getLayout());

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }


}
